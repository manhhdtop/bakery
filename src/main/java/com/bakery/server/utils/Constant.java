package com.bakery.server.utils;

import java.util.ArrayList;
import java.util.List;

public interface Constant {

    class RESULT_SET_MAPPING {
        public static final String PRODUCT_RESPONSE = "PRODUCT_RESPONSE_MAPPING";
        public static final String PRODUCT_PRICE_RANGE_RESPONSE = "PRODUCT_PRICE_RANGE_RESPONSE_MAPPING";
    }

    class CONTENT_TYPE_FILE {
        public static final String PNG = "image/png";
        public static final String JPEG = "image/jpeg";
        public static final String JPG = "image/jpg";
        public static final String IMAGE_GIF = "image/gif";
        public static final String PDF = "application/pdf";
        public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        public static final String DOC = "application/msword";
        public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        public static final String XLS = "application/vnd.ms-excel";
        public static final String ZIP_1 = "application/zip";
        public static final String ZAR = "application/vnd.rar";
    }

    class FILE_TYPE {
        public static List<String> IMAGE = new ArrayList<>();
        public static List<String> WORD = new ArrayList<>();
        public static List<String> EXCEL = new ArrayList<>();
        public static List<String> DOCUMENT = new ArrayList<>();
        public static List<String> COMPRESSION = new ArrayList<>();
        public static List<String> ALL = new ArrayList<>();

        static {
            // Image
            IMAGE.add(CONTENT_TYPE_FILE.PNG);
            IMAGE.add(CONTENT_TYPE_FILE.JPG);
            IMAGE.add(CONTENT_TYPE_FILE.JPEG);
            IMAGE.add(CONTENT_TYPE_FILE.IMAGE_GIF);
            // Word
            WORD.add(CONTENT_TYPE_FILE.DOC);
            WORD.add(CONTENT_TYPE_FILE.DOCX);
            // Excel
            WORD.add(CONTENT_TYPE_FILE.XLS);
            WORD.add(CONTENT_TYPE_FILE.XLSX);
            // Document
            DOCUMENT.addAll(WORD);
            DOCUMENT.addAll(EXCEL);
            // Compression
            COMPRESSION.add(CONTENT_TYPE_FILE.ZIP_1);
            COMPRESSION.add(CONTENT_TYPE_FILE.ZAR);

            // All type
            ALL.addAll(DOCUMENT);
            ALL.addAll(IMAGE);
            ALL.addAll(COMPRESSION);
        }
    }

    class SqlType {
        public static final boolean COUNTING = true;
        public static final boolean LIST = false;
    }

    class VoucherType {
        public static final Integer AMOUNT = 1;
        public static final Integer PERCENT = 2;
    }
}
