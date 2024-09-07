grammar SPRGEN;

// Gramatica Analisador Léxico
LONG: ('0'..'9')+;
PALAVRA_CHAVE: 'entidades' | 'endpoints' | 'GET' | 'POST' | 'PUT' | 'DELETE';
TIPO: 'String' | 'Long' | 'boolean';
DOIS_PONTOS: ':';
ATRIBUI: '=';
OPEN_LIST: '[';
CLOSE_LIST: ']';
OPEN_OBJECT: '{';
CLOSE_OBJECT: '}';
WS: ( ' ' | '\t' | '\r' | '\n' ) -> skip;
IDENT: ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')*;
STRING: '"' ( ~('\n'|'\r') )*? '"';
COMMENT: '//' ( ~('\n'|'\r') )*? ('\n'|'\r') -> skip;

// Erros léxicos
UNCLOSED_STRING: ('"') ~('"')*? ('\n'|'\r');
INVALID_CHAR: .+?;

// Gramatica Analisador Sintático

programa: entidades endpoints EOF;

entidades: 'entidades' ATRIBUI '[' entidade+ ']';
entidade: IDENT ATRIBUI '{' (campos+=campo)+ '}';
campo: ident=IDENT ':' (tipo=TIPO | tipo=IDENT);

endpoints: 'endpoints' ATRIBUI '[' endpoint+ ']';
endpoint: IDENT ATRIBUI '{' (rotas+=rota)+ '}';
rota: metodoHttp STRING;
metodoHttp: 'GET' | 'POST' | 'PUT' | 'DELETE';
