package io.spin.status.config;

public class Constants {

    public final static String SESSION_IDENTIFIRE = "SESSION_INFO";

    public final static String COOKIE_TYPE = "TYPE";
    public final static String COOKIE_LAST_PASSWORD = "LAST_PASSWORD";

    public final static String IMAGES_DOMAIN = "https://images.spin.io/";
    public final static String BUCKET_PREFIX_AD_IMAGES = "0_admin/ads/";
    public final static String BUCKET_PREFIX_RAFFLE_IMAGES = "0_admin/raffle/";
    public final static String BUCKET_PREFIX_BANNER_IMAGES = "0_admin/banner/";
    public final static String BUCKET_PREFIX_NOTICE_IMAGES = "0_admin/notice/";
    public final static String BUCKET_PREFIX_PRIZE_IMAGES = "0_admin/prize/";
    public final static String BUCKET_PREFIX_COINBOX_IMAGES = "0_admin/coinbox/";

    public final static String SOCKET_MESSAGE_DECRYPT_ERROR = "복호화에 실패했습니다.";
    public final static String SOCKET_MESSAGE_JOB_START = "새로운 작업을 시작 합니다.";
    public final static String SOCKET_MESSAGE_JOB_COMPLETE = "모든 작업을 완료 하였습니다.\n반드시 정상적으로 모든 요청이 처리 되었는지 확인해 주시기 바랍니다.";
    public final static String SOCKET_MESSAGE_DYNAMODB_CAPACITY_INCREASE = "DynamoDB 처리량을 증가 시킵니다.\n약 20초 정도 소요 됩니다.";
    public final static String SOCKET_MESSAGE_DYNAMODB_CAPACITY_DECREASE = "DynamoDB 처리량을 감소 시킵니다.\n약 20초 정도 소요 됩니다.";
    public final static String SOCKET_MESSAGE_READ_EXCEL = "엑셀 파일을 분석 합니다.";
    public final static String SOCKET_MESSAGE_READ_EXCEL_COMPLETE = "엑셀 파일로 부터 지급 할 정보를 모두 추출 하였습니다.";

}
