package br.ufscar.dc.compiladores.sprgen;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.BitSet;

public class ErrorListener implements ANTLRErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                            int charPositionInLine, String msg, RecognitionException e) {
        Token token = (Token) offendingSymbol;
        if (Boolean.TRUE.equals(ValidateErrorHelper.isError(token.getType()))) {
            System.out.println("token no syntaxError: " + token.getText() + ", " + token.getType());
            ValidateErrorHelper.errosSintaticos.add(ValidateErrorHelper.stringifyError(token));
            System.out.println(ValidateErrorHelper.stringifyError(token));
        } else {
            ValidateErrorHelper.errosSintaticos.add(ValidateErrorHelper.stringifySyntaxError(line, token));
            System.out.println(ValidateErrorHelper.stringifySyntaxError(line, token));
        }
        throw new ParseCancellationException();
    }

    @Override
    public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean b, BitSet bitSet, ATNConfigSet atnConfigSet) {
        // Not being used
    }

    @Override
    public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitSet, ATNConfigSet atnConfigSet) {
        // Not being used
    }

    @Override
    public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atnConfigSet) {
        // Not being used
    }
}
