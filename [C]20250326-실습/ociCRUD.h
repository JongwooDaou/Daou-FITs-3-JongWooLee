#pragma once

#include <oci.h>

// DB 연결 정보 구조체
typedef struct {
    OCIEnv* envhp;
    OCIError* errhp;
    OCISvcCtx* svchp;
    OCISession* usrhp;
    OCIServer* srvhp;
} OCIContext;

typedef struct {
    int id;
    char customerName[50];
    char stockName[50];
    char type;
    int quantity;
    double price;
} stockTransaction;

int initOCIContext(OCIContext* ctx);
void freeOCIContext(OCIContext* ctx);

// CRUD 함수
void ociInsert(OCIContext* ctx);
void ociSelectAll(OCIContext* ctx);
void ociSelectByName(OCIContext* ctx);
void ociUpdateById(OCIContext* ctx);
void ociDeleteById(OCIContext* ctx);