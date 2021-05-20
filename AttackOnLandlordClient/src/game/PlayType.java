package game;

public enum PlayType {

	p1,//single
	p2,//double
	joker,//joker bomb
	p3,//triple
	bomb,//regular bomb
	pn,//continuous list(length>=5)
	p31,//triple+single
	p32,//triple+double
	p112233,//multi double
	p111222,//multi triple
	p11122234,//2*triple+2*single
	p1112223344,//2*triple+2*double
	invalid//invalid play
	
}
