package com.cyberday1.theexpanse.mixinextras.ap;

import com.cyberday1.theexpanse.mixinextras.expression.Definition;
import com.cyberday1.theexpanse.mixinextras.expression.Definitions;
import com.cyberday1.theexpanse.mixinextras.expression.Expression;
import com.cyberday1.theexpanse.mixinextras.expression.Expressions;
import com.cyberday1.theexpanse.mixinextras.ap.expressions.DefinitionInfo;
import com.cyberday1.theexpanse.mixinextras.injector.ModifyExpressionValueInjectionInfo;
import com.cyberday1.theexpanse.mixinextras.injector.ModifyReceiverInjectionInfo;
import com.cyberday1.theexpanse.mixinextras.injector.ModifyReturnValueInjectionInfo;
import com.cyberday1.theexpanse.mixinextras.injector.WrapWithConditionV1InjectionInfo;
import com.cyberday1.theexpanse.mixinextras.injector.v2.WrapWithConditionInjectionInfo;
import com.cyberday1.theexpanse.mixinextras.injector.wrapmethod.WrapMethodInjectionInfo;
import com.cyberday1.theexpanse.mixinextras.injector.wrapoperation.WrapOperationInjectionInfo;
import com.cyberday1.theexpanse.mixinextras.utils.MixinAPInternals;
import com.cyberday1.theexpanse.mixinextras.utils.MixinAPVersion;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.util.logging.MessageRouter;
import org.spongepowered.asm.util.asm.IAnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MixinExtrasAP extends AbstractProcessor {
    private static final boolean MIXIN = setupMixin();

    private final List<DefinitionInfo> definitions = new ArrayList<>();

    private static boolean setupMixin() {
        try {
            // Use a simple logger until Mixin sets up its own.
            MessageRouter.setMessager(new StdoutMessager());
        } catch (NoClassDefFoundError e) {
            // Mixin AP probably isn't available, e.g. because Loom has excluded it from IDEA.
            return false;
        }
        return true;
    }

    static {
        if (MIXIN) {
            registerInjectors();
        }
    }

    private static void registerInjectors() {
        InjectionInfo.register(ModifyExpressionValueInjectionInfo.class);
        InjectionInfo.register(ModifyReceiverInjectionInfo.class);
        InjectionInfo.register(ModifyReturnValueInjectionInfo.class);
        InjectionInfo.register(WrapMethodInjectionInfo.class);
        InjectionInfo.register(WrapOperationInjectionInfo.class);
        InjectionInfo.register(WrapWithConditionV1InjectionInfo.class);
        InjectionInfo.register(WrapWithConditionInjectionInfo.class);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!MIXIN) {
            return false;
        }
        MixinAPVersion.check(processingEnv);
        if (roundEnv.processingOver()) {
            remapDefinitions();
            return true;
        }
        gatherDefinitions(roundEnv);
        return true;
    }

    private void gatherDefinitions(RoundEnvironment roundEnv) {
        for (Element elem : roundEnv.getElementsAnnotatedWith(Definition.class)) {
            AnnotationHandle def = AnnotationHandle.of(elem, Definition.class);
            registerDefinition(elem, def);
        }
        for (Element elem : roundEnv.getElementsAnnotatedWith(Definitions.class)) {
            AnnotationHandle defs = AnnotationHandle.of(elem, Definitions.class);
            for (IAnnotationHandle def : defs.getAnnotationList("value")) {
                registerDefinition(elem, (AnnotationHandle) def);
            }
        }
    }

    private void registerDefinition(Element handler, AnnotationHandle def) {
        TypeElement mixin = (TypeElement) handler.getEnclosingElement();
        AnnotationHandle injector = getInjectorAnnotation(handler);
        Boolean remap = def.getValue("remap");
        for (String method : def.<String>getList("method")) {
            definitions.add(new DefinitionInfo.Method(
                    processingEnv,
                    mixin,
                    (ExecutableElement) handler,
                    injector,
                    method,
                    remap
            ));
        }
        for (String field : def.<String>getList("field")) {
            definitions.add(new DefinitionInfo.Field(
                    processingEnv,
                    mixin,
                    (ExecutableElement) handler,
                    injector,
                    field,
                    remap
            ));
        }
    }

    private void remapDefinitions() {
        for (DefinitionInfo def : definitions) {
            def.remap();
        }
        MixinAPInternals.writeReferences(processingEnv);
    }

    private AnnotationHandle getInjectorAnnotation(Element handler) {
        return InjectionInfo.getRegisteredAnnotations()
                .stream()
                .map(it -> AnnotationHandle.of(handler, it))
                .filter(AnnotationHandle::exists)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find injector annotation on " + handler));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        List<Class<? extends Annotation>> types = Arrays.asList(
                Expression.class,
                Expressions.class,
                Definition.class,
                Definitions.class
        );
        return types.stream().map(Class::getName).collect(Collectors.toSet());
    }
}
