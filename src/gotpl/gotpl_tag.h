#include "gotpl.h"
#include "gotpl_io.h"

#ifndef __GOTPL_TAG__H
#define __GOTPL_TAG__H

typedef gotpl_bool (*gotpl_tag_execute_t)(gotpl_tag* owner, gotpl_tag* parent,
		gotpl_tag_context* context, gotpl_output_stream* out);

//Used on template create to initialize struct.
typedef gotpl_bool (*gotpl_tag_init_t)(gotpl_tag* owner, gotpl_i8* cmd_line);
typedef gotpl_bool (*gotpl_tag_destroy_t)(gotpl_tag* owner);

struct gotpl_tag {
	gotpl_i8* name;
	gotpl_ui8* _private;
	gotpl_tag_list* children;
	gotpl_tag_init_t init;
	gotpl_tag_execute_t execute;
	gotpl_tag_destroy_t destroy;
};

#endif