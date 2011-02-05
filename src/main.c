#include "gotpl/gotpl_io.h"
#include <stdio.h>

int test() {
	gotpl_ui ui = 0;
	ui++;
	return ui;
}

int main() {
	test();
	gotpl_input_stream is;
	gotpl_output_stream os;

	gotpl_create_std_output_stream(&os, "test.txt");
	os.write(&os, "mine", strlen("mine"));
	os.write(&os, "Τη γλώσσα μου έδωσαν ελληνική"
		"το σπίτι φτωχικό στις αμμουδιές του Ομήρου."
		"Μονάχη έγνοια η γλώσσα μου στις αμμουδιές του Ομήρου.", strlen(
			"Τη γλώσσα μου έδωσαν ελληνική"
				"το σπίτι φτωχικό στις αμμουδιές του Ομήρου."
				"Μονάχη έγνοια η γλώσσα μου στις αμμουδιές του Ομήρου."));
	os.write(&os, "mine !!!!", strlen("mine !!!!"));
	os.write(&os, "На берегу пустынных волн\r\n"
		"Стоял он, дум великих полн,\r\n"
		"И вдаль глядел. Пред ним широко\r\n"
		"Река неслася; бедный чёлн\r\n"
		"По ней стремился одиноко.\r\n"
		"По мшистым, топким берегам\r\n"
		"Чернели избы здесь и там,\r\n"
		"Приют убогого чухонца;\r\n"
		"И лес, неведомый лучам\r\n"
		"В тумане спрятанного солнца,\r\n"
		"Кругом шумел.", strlen("На берегу пустынных волн\r\n"
		"Стоял он, дум великих полн,\r\n"
		"И вдаль глядел. Пред ним широко\r\n"
		"Река неслася; бедный чёлн\r\n"
		"По ней стремился одиноко.\r\n"
		"По мшистым, топким берегам\r\n"
		"Чернели избы здесь и там,\r\n"
		"Приют убогого чухонца;\r\n"
		"И лес, неведомый лучам\r\n"
		"В тумане спрятанного солнца,\r\n"
		"Кругом шумел."));
	os.write(&os, "mine !!!!", strlen("mine !!!!"));
	os.write(
			&os,
			"யாமறிந்த மொழிகளிலே தமிழ்மொழி போல் இனிதாவது எங்கும் காணோம்,\r\n"
				"பாமரராய் விலங்குகளாய், உலகனைத்தும் இகழ்ச்சிசொலப் பான்மை கெட்டு,\r\n"
				"நாமமது தமிழரெனக் கொண்டு இங்கு வாழ்ந்திடுதல் நன்றோ? சொல்லீர்!\r\n"
				"தேமதுரத் தமிழோசை உலகமெலாம் பரவும்வகை செய்தல் வேண்டும். ",
			strlen(
					"யாமறிந்த மொழிகளிலே தமிழ்மொழி போல் இனிதாவது எங்கும் காணோம்,\r\n"
						"பாமரராய் விலங்குகளாய், உலகனைத்தும் இகழ்ச்சிசொலப் பான்மை கெட்டு,\r\n"
						"நாமமது தமிழரெனக் கொண்டு இங்கு வாழ்ந்திடுதல் நன்றோ? சொல்லீர்!\r\n"
						"தேமதுரத் தமிழோசை உலகமெலாம் பரவும்வகை செய்தல் வேண்டும். "));
	os.write(&os, "ᛖᚴ ᚷᛖᛏ ᛖᛏᛁ ᚧ ᚷᛚᛖᚱ ᛘᚾ ᚦᛖᛋᛋ ᚨᚧ ᚡᛖ ᚱᚧᚨ ᛋᚨᚱ ", strlen(
			"ᛖᚴ ᚷᛖᛏ ᛖᛏᛁ ᚧ ᚷᛚᛖᚱ ᛘᚾ ᚦᛖᛋᛋ ᚨᚧ ᚡᛖ ᚱᚧᚨ ᛋᚨᚱ "));
	os.write(&os, "mine !!!!", strlen("mine !!!!"));
	os.close(&os);

	gotpl_create_std_input_stream(&is, "test.txt", gotpl_enc_utf8);

	gotpl_i count;
	while (is.has_more(&is)) {
		count = is.read(&is);
		printf("%i", count);
	}
	return 0;
}