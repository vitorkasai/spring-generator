// Generated from java-escape by ANTLR 4.11.1
package br.ufscar.dc.compiladores.sprgen;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class SPRGENParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, LONG=7, PALAVRA_CHAVE=8, 
		TIPO=9, DOIS_PONTOS=10, ATRIBUI=11, OPEN_LIST=12, CLOSE_LIST=13, OPEN_OBJECT=14, 
		CLOSE_OBJECT=15, WS=16, IDENT=17, STRING=18, COMMENT=19, UNCLOSED_STRING=20, 
		INVALID_CHAR=21;
	public static final int
		RULE_programa = 0, RULE_entidades = 1, RULE_entidade = 2, RULE_campo = 3, 
		RULE_endpoints = 4, RULE_endpoint = 5, RULE_rota = 6, RULE_metodoHttp = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"programa", "entidades", "entidade", "campo", "endpoints", "endpoint", 
			"rota", "metodoHttp"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'entidades'", "'endpoints'", "'GET'", "'POST'", "'PUT'", "'DELETE'", 
			null, null, null, "':'", "'='", "'['", "']'", "'{'", "'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, "LONG", "PALAVRA_CHAVE", "TIPO", 
			"DOIS_PONTOS", "ATRIBUI", "OPEN_LIST", "CLOSE_LIST", "OPEN_OBJECT", "CLOSE_OBJECT", 
			"WS", "IDENT", "STRING", "COMMENT", "UNCLOSED_STRING", "INVALID_CHAR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "java-escape"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SPRGENParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramaContext extends ParserRuleContext {
		public EntidadesContext entidades() {
			return getRuleContext(EntidadesContext.class,0);
		}
		public EndpointsContext endpoints() {
			return getRuleContext(EndpointsContext.class,0);
		}
		public TerminalNode EOF() { return getToken(SPRGENParser.EOF, 0); }
		public ProgramaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_programa; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).enterPrograma(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).exitPrograma(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SPRGENVisitor ) return ((SPRGENVisitor<? extends T>)visitor).visitPrograma(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramaContext programa() throws RecognitionException {
		ProgramaContext _localctx = new ProgramaContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_programa);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			entidades();
			setState(17);
			endpoints();
			setState(18);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EntidadesContext extends ParserRuleContext {
		public TerminalNode ATRIBUI() { return getToken(SPRGENParser.ATRIBUI, 0); }
		public TerminalNode OPEN_LIST() { return getToken(SPRGENParser.OPEN_LIST, 0); }
		public TerminalNode CLOSE_LIST() { return getToken(SPRGENParser.CLOSE_LIST, 0); }
		public List<EntidadeContext> entidade() {
			return getRuleContexts(EntidadeContext.class);
		}
		public EntidadeContext entidade(int i) {
			return getRuleContext(EntidadeContext.class,i);
		}
		public EntidadesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entidades; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).enterEntidades(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).exitEntidades(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SPRGENVisitor ) return ((SPRGENVisitor<? extends T>)visitor).visitEntidades(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EntidadesContext entidades() throws RecognitionException {
		EntidadesContext _localctx = new EntidadesContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_entidades);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
			match(T__0);
			setState(21);
			match(ATRIBUI);
			setState(22);
			match(OPEN_LIST);
			setState(24); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(23);
				entidade();
				}
				}
				setState(26); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IDENT );
			setState(28);
			match(CLOSE_LIST);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EntidadeContext extends ParserRuleContext {
		public CampoContext campo;
		public List<CampoContext> campos = new ArrayList<CampoContext>();
		public TerminalNode IDENT() { return getToken(SPRGENParser.IDENT, 0); }
		public TerminalNode ATRIBUI() { return getToken(SPRGENParser.ATRIBUI, 0); }
		public TerminalNode OPEN_OBJECT() { return getToken(SPRGENParser.OPEN_OBJECT, 0); }
		public TerminalNode CLOSE_OBJECT() { return getToken(SPRGENParser.CLOSE_OBJECT, 0); }
		public List<CampoContext> campo() {
			return getRuleContexts(CampoContext.class);
		}
		public CampoContext campo(int i) {
			return getRuleContext(CampoContext.class,i);
		}
		public EntidadeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entidade; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).enterEntidade(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).exitEntidade(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SPRGENVisitor ) return ((SPRGENVisitor<? extends T>)visitor).visitEntidade(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EntidadeContext entidade() throws RecognitionException {
		EntidadeContext _localctx = new EntidadeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_entidade);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(IDENT);
			setState(31);
			match(ATRIBUI);
			setState(32);
			match(OPEN_OBJECT);
			setState(34); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(33);
				((EntidadeContext)_localctx).campo = campo();
				((EntidadeContext)_localctx).campos.add(((EntidadeContext)_localctx).campo);
				}
				}
				setState(36); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IDENT );
			setState(38);
			match(CLOSE_OBJECT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CampoContext extends ParserRuleContext {
		public Token ident;
		public Token tipo;
		public TerminalNode DOIS_PONTOS() { return getToken(SPRGENParser.DOIS_PONTOS, 0); }
		public List<TerminalNode> IDENT() { return getTokens(SPRGENParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(SPRGENParser.IDENT, i);
		}
		public TerminalNode TIPO() { return getToken(SPRGENParser.TIPO, 0); }
		public CampoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_campo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).enterCampo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).exitCampo(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SPRGENVisitor ) return ((SPRGENVisitor<? extends T>)visitor).visitCampo(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CampoContext campo() throws RecognitionException {
		CampoContext _localctx = new CampoContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_campo);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			((CampoContext)_localctx).ident = match(IDENT);
			setState(41);
			match(DOIS_PONTOS);
			setState(44);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TIPO:
				{
				setState(42);
				((CampoContext)_localctx).tipo = match(TIPO);
				}
				break;
			case IDENT:
				{
				setState(43);
				((CampoContext)_localctx).tipo = match(IDENT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EndpointsContext extends ParserRuleContext {
		public TerminalNode ATRIBUI() { return getToken(SPRGENParser.ATRIBUI, 0); }
		public TerminalNode OPEN_LIST() { return getToken(SPRGENParser.OPEN_LIST, 0); }
		public TerminalNode CLOSE_LIST() { return getToken(SPRGENParser.CLOSE_LIST, 0); }
		public List<EndpointContext> endpoint() {
			return getRuleContexts(EndpointContext.class);
		}
		public EndpointContext endpoint(int i) {
			return getRuleContext(EndpointContext.class,i);
		}
		public EndpointsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endpoints; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).enterEndpoints(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).exitEndpoints(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SPRGENVisitor ) return ((SPRGENVisitor<? extends T>)visitor).visitEndpoints(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EndpointsContext endpoints() throws RecognitionException {
		EndpointsContext _localctx = new EndpointsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_endpoints);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(T__1);
			setState(47);
			match(ATRIBUI);
			setState(48);
			match(OPEN_LIST);
			setState(50); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(49);
				endpoint();
				}
				}
				setState(52); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IDENT );
			setState(54);
			match(CLOSE_LIST);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EndpointContext extends ParserRuleContext {
		public RotaContext rota;
		public List<RotaContext> rotas = new ArrayList<RotaContext>();
		public TerminalNode IDENT() { return getToken(SPRGENParser.IDENT, 0); }
		public TerminalNode ATRIBUI() { return getToken(SPRGENParser.ATRIBUI, 0); }
		public TerminalNode OPEN_OBJECT() { return getToken(SPRGENParser.OPEN_OBJECT, 0); }
		public TerminalNode CLOSE_OBJECT() { return getToken(SPRGENParser.CLOSE_OBJECT, 0); }
		public List<RotaContext> rota() {
			return getRuleContexts(RotaContext.class);
		}
		public RotaContext rota(int i) {
			return getRuleContext(RotaContext.class,i);
		}
		public EndpointContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endpoint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).enterEndpoint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).exitEndpoint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SPRGENVisitor ) return ((SPRGENVisitor<? extends T>)visitor).visitEndpoint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EndpointContext endpoint() throws RecognitionException {
		EndpointContext _localctx = new EndpointContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_endpoint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(IDENT);
			setState(57);
			match(ATRIBUI);
			setState(58);
			match(OPEN_OBJECT);
			setState(60); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(59);
				((EndpointContext)_localctx).rota = rota();
				((EndpointContext)_localctx).rotas.add(((EndpointContext)_localctx).rota);
				}
				}
				setState(62); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((_la) & ~0x3f) == 0 && ((1L << _la) & 120L) != 0 );
			setState(64);
			match(CLOSE_OBJECT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RotaContext extends ParserRuleContext {
		public MetodoHttpContext metodoHttp() {
			return getRuleContext(MetodoHttpContext.class,0);
		}
		public TerminalNode STRING() { return getToken(SPRGENParser.STRING, 0); }
		public RotaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rota; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).enterRota(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).exitRota(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SPRGENVisitor ) return ((SPRGENVisitor<? extends T>)visitor).visitRota(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RotaContext rota() throws RecognitionException {
		RotaContext _localctx = new RotaContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_rota);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			metodoHttp();
			setState(67);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MetodoHttpContext extends ParserRuleContext {
		public MetodoHttpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_metodoHttp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).enterMetodoHttp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPRGENListener ) ((SPRGENListener)listener).exitMetodoHttp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SPRGENVisitor ) return ((SPRGENVisitor<? extends T>)visitor).visitMetodoHttp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MetodoHttpContext metodoHttp() throws RecognitionException {
		MetodoHttpContext _localctx = new MetodoHttpContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_metodoHttp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			_la = _input.LA(1);
			if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 120L) != 0) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0015H\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0004\u0001\u0019\b\u0001\u000b\u0001\f\u0001\u001a"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0004\u0002#\b\u0002\u000b\u0002\f\u0002$\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003-\b\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0004\u00043\b\u0004\u000b"+
		"\u0004\f\u00044\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0004\u0005=\b\u0005\u000b\u0005\f\u0005>\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0000\u0000\b\u0000\u0002\u0004\u0006\b\n\f\u000e\u0000\u0001"+
		"\u0001\u0000\u0003\u0006D\u0000\u0010\u0001\u0000\u0000\u0000\u0002\u0014"+
		"\u0001\u0000\u0000\u0000\u0004\u001e\u0001\u0000\u0000\u0000\u0006(\u0001"+
		"\u0000\u0000\u0000\b.\u0001\u0000\u0000\u0000\n8\u0001\u0000\u0000\u0000"+
		"\fB\u0001\u0000\u0000\u0000\u000eE\u0001\u0000\u0000\u0000\u0010\u0011"+
		"\u0003\u0002\u0001\u0000\u0011\u0012\u0003\b\u0004\u0000\u0012\u0013\u0005"+
		"\u0000\u0000\u0001\u0013\u0001\u0001\u0000\u0000\u0000\u0014\u0015\u0005"+
		"\u0001\u0000\u0000\u0015\u0016\u0005\u000b\u0000\u0000\u0016\u0018\u0005"+
		"\f\u0000\u0000\u0017\u0019\u0003\u0004\u0002\u0000\u0018\u0017\u0001\u0000"+
		"\u0000\u0000\u0019\u001a\u0001\u0000\u0000\u0000\u001a\u0018\u0001\u0000"+
		"\u0000\u0000\u001a\u001b\u0001\u0000\u0000\u0000\u001b\u001c\u0001\u0000"+
		"\u0000\u0000\u001c\u001d\u0005\r\u0000\u0000\u001d\u0003\u0001\u0000\u0000"+
		"\u0000\u001e\u001f\u0005\u0011\u0000\u0000\u001f \u0005\u000b\u0000\u0000"+
		" \"\u0005\u000e\u0000\u0000!#\u0003\u0006\u0003\u0000\"!\u0001\u0000\u0000"+
		"\u0000#$\u0001\u0000\u0000\u0000$\"\u0001\u0000\u0000\u0000$%\u0001\u0000"+
		"\u0000\u0000%&\u0001\u0000\u0000\u0000&\'\u0005\u000f\u0000\u0000\'\u0005"+
		"\u0001\u0000\u0000\u0000()\u0005\u0011\u0000\u0000),\u0005\n\u0000\u0000"+
		"*-\u0005\t\u0000\u0000+-\u0005\u0011\u0000\u0000,*\u0001\u0000\u0000\u0000"+
		",+\u0001\u0000\u0000\u0000-\u0007\u0001\u0000\u0000\u0000./\u0005\u0002"+
		"\u0000\u0000/0\u0005\u000b\u0000\u000002\u0005\f\u0000\u000013\u0003\n"+
		"\u0005\u000021\u0001\u0000\u0000\u000034\u0001\u0000\u0000\u000042\u0001"+
		"\u0000\u0000\u000045\u0001\u0000\u0000\u000056\u0001\u0000\u0000\u0000"+
		"67\u0005\r\u0000\u00007\t\u0001\u0000\u0000\u000089\u0005\u0011\u0000"+
		"\u00009:\u0005\u000b\u0000\u0000:<\u0005\u000e\u0000\u0000;=\u0003\f\u0006"+
		"\u0000<;\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000><\u0001\u0000"+
		"\u0000\u0000>?\u0001\u0000\u0000\u0000?@\u0001\u0000\u0000\u0000@A\u0005"+
		"\u000f\u0000\u0000A\u000b\u0001\u0000\u0000\u0000BC\u0003\u000e\u0007"+
		"\u0000CD\u0005\u0012\u0000\u0000D\r\u0001\u0000\u0000\u0000EF\u0007\u0000"+
		"\u0000\u0000F\u000f\u0001\u0000\u0000\u0000\u0005\u001a$,4>";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}