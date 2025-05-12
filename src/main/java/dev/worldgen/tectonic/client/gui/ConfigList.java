package dev.worldgen.tectonic.client.gui;

import dev.worldgen.tectonic.client.gui.widget.SliderWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;
import java.util.function.Consumer;

import static dev.worldgen.tectonic.client.gui.ConfigScreen.option;

public class ConfigList extends ContainerObjectSelectionList<ConfigList.Entry> {
    public ConfigList(Minecraft minecraft, int width, ConfigScreen parent) {
        super(minecraft, width, parent.layout.getContentHeight(), parent.layout.getHeaderHeight(), 25);
    }

    public void addCategory(String name, Font font) {
        this.addEntry(new StringWidget(ConfigScreen.text("category."+name), font));
    }

    public void addBoolean(String name, Consumer<Boolean> setter, boolean value, boolean base) {
        CycleButton.Builder<Boolean> button = CycleButton.onOffBuilder(value);

        MutableComponent text = Component.empty();
        text.append(option(name + ".tooltip"));
        text.append(CommonComponents.NEW_LINE);
        text.append(Component.translatable("config.tectonic.default"));
        text.append(base ? "§eON" : "§eOFF");

        button.withTooltip(__ -> Tooltip.create(text));

        this.addEntry(button.create(option(name), (__, bool) -> setter.accept(bool)));
    }

    public void addInteger(String name, double min, double max, Consumer<Integer> action, double value, double base) {
        this.addEntry(new SliderWidget(min, max, 1, "option." + name, newValue -> action.accept(newValue.intValue()), value, true, base));
    }

    public void addDouble(String name, double min, double max, double step, Consumer<Double> action, double value, double base) {
        this.addEntry(new SliderWidget(min, max, step, "option." + name, action, value, false, base));
    }

    public void addEntry(AbstractWidget widget) {
        widget.setX(width / 2 - 155);
        widget.setY(0);
        widget.setHeight(20);
        widget.setWidth(310);
        this.addEntry(new Entry(widget));
    }

    public int getRowWidth() {
        return 310;
    }

    public void updateSize(int width, HeaderAndFooterLayout layout) {
        super.updateSize(width, layout);
        this.children().forEach(entry -> entry.widget.setX(width / 2 - 155));
    }

    static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
        final AbstractWidget widget;

        Entry(AbstractWidget widget) {
            this.widget = widget;
        }

        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            this.widget.setY(top);
            this.widget.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        public List<? extends GuiEventListener> children() {
            return List.of(this.widget);
        }

        public List<? extends NarratableEntry> narratables() {
            return List.of(this.widget);
        }
    }
}
