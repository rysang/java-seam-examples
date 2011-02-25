#include "gotpl/gotpl_template.h"
#include "gotpl/gotpl_tag_map.h"

struct gotpl_template {
	gotpl_pool* pool;
	gotpl_tag_map* tags;
};

gotpl_template* gotpl_template_create(gotpl_pool* pool, gotpl_tag* addons,
		gotpl_ui addons_count) {
	return 0;
}
