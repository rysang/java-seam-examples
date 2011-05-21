#include "pools/oglPool.h"
#include "init/oglInit.h"

int main(int argc, char** argv) {
	oglPool pool(1024 * 1024);

	oglInit init(argc, argv);
	init.goMain();

	return 0;
}
