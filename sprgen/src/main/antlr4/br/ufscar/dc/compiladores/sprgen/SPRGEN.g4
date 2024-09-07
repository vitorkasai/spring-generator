grammar SPRGEN;

NUMBER: ('0'..'9')+ ('.' ('0'..'9')+)?;

program: NUMBER EOF;