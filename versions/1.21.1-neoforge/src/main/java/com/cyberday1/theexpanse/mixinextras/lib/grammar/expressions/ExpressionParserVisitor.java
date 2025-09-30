// Generated from expressions/src/main/antlr/ExpressionParser.g4 by ANTLR 4.13.1
package com.cyberday1.theexpanse.mixinextras.lib.grammar.expressions;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExpressionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExpressionParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#root}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoot(ExpressionParser.RootContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MemberAssignmentStatement}
	 * labeled alternative in {@link ExpressionParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAssignmentStatement(ExpressionParser.MemberAssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayStoreStatement}
	 * labeled alternative in {@link ExpressionParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayStoreStatement(ExpressionParser.ArrayStoreStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierAssignmentStatement}
	 * labeled alternative in {@link ExpressionParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierAssignmentStatement(ExpressionParser.IdentifierAssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link ExpressionParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(ExpressionParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ThrowStatement}
	 * labeled alternative in {@link ExpressionParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowStatement(ExpressionParser.ThrowStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionStatement}
	 * labeled alternative in {@link ExpressionParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(ExpressionParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BitwiseXorExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseXorExpression(ExpressionParser.BitwiseXorExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ClassConstantExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassConstantExpression(ExpressionParser.ClassConstantExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StaticMethodCallExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStaticMethodCallExpression(ExpressionParser.StaticMethodCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolLitExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLitExpression(ExpressionParser.BoolLitExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(ExpressionParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FreeMethodReferenceExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFreeMethodReferenceExpression(ExpressionParser.FreeMethodReferenceExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstructorReferenceExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorReferenceExpression(ExpressionParser.ConstructorReferenceExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstantiationExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstantiationExpression(ExpressionParser.InstantiationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntLitExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLitExpression(ExpressionParser.IntLitExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ThisExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisExpression(ExpressionParser.ThisExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DecimalLitExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimalLitExpression(ExpressionParser.DecimalLitExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MethodCallExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCallExpression(ExpressionParser.MethodCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceofExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceofExpression(ExpressionParser.InstanceofExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WildcardExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWildcardExpression(ExpressionParser.WildcardExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayLitExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayLitExpression(ExpressionParser.ArrayLitExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringLitExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLitExpression(ExpressionParser.StringLitExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(ExpressionParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MultiplicativeExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(ExpressionParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BitwiseOrExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseOrExpression(ExpressionParser.BitwiseOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenthesizedExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedExpression(ExpressionParser.ParenthesizedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AdditiveExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(ExpressionParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MemberAccessExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAccessExpression(ExpressionParser.MemberAccessExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoundMethodReferenceExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoundMethodReferenceExpression(ExpressionParser.BoundMethodReferenceExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ShiftExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpression(ExpressionParser.ShiftExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CapturingExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCapturingExpression(ExpressionParser.CapturingExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NullExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullExpression(ExpressionParser.NullExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExpression(ExpressionParser.IdentifierExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BitwiseAndExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseAndExpression(ExpressionParser.BitwiseAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ComparisonExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonExpression(ExpressionParser.ComparisonExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SuperCallExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperCallExpression(ExpressionParser.SuperCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CastExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpression(ExpressionParser.CastExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewArrayExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayExpression(ExpressionParser.NewArrayExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayAccessExpression}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccessExpression(ExpressionParser.ArrayAccessExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierName}
	 * labeled alternative in {@link ExpressionParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierName(ExpressionParser.IdentifierNameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WildcardName}
	 * labeled alternative in {@link ExpressionParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWildcardName(ExpressionParser.WildcardNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#nameWithDims}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameWithDims(ExpressionParser.NameWithDimsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(ExpressionParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#nonEmptyArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonEmptyArguments(ExpressionParser.NonEmptyArgumentsContext ctx);
}