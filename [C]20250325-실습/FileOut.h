#pragma once

#include "ociCrud.h" 

void saveToBinary(OCIContext* ctx, const char* filename);
void saveToCSV(OCIContext* ctx, const char* filename);
