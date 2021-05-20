module AttackOnLandlordServer {
	requires fastjson;
	requires java.sql;
	requires jdk.jshell;
	

    requires jdk.unsupported;
    
    opens model;
    exports model;
    

    
}