package io.spin.status.enumeration;

import lombok.Getter;

@Getter
public enum ResultCode {

    OK("OK", "정상처리"),

    E001("E001", "확인 할 수 없습니다."),

    E801("E801", "이미 아이디가 있습니다."),
    E802("E802", "수정할 대상의 아이디를 찾을 수 없습니다."),
    E803("E803", "비밀번호와 비밀번호 확인이 매칭되지 않습니다."),
    E804("E804", "타입에 해당하는 ID 가 아닙니다."),
    E805("E805", "ID 가 필요 합니다."),
    E806("E806", "기록된 파트너 코드가 없습니다."),
    E807("E807", "밴 기록이 없습니다."),
    E808("E808", "이미 같은 정보로 등록된 밴 기록이 있습니다."),

    E951("E951", "관리자를 찾을 수 없습니다."),

    E993("E993", "입력하신 날짜에 해당 클릭수가 이미 존재합니다"),
    E994("E994", "복호화에 실패했습니다."),
    E995("E995", "이미 작업중인 배치 작업이 있습니다.\n선행된 작업이 종료 되어야 합니다."),
    E996("E996", "반드시 이미지가 필요 합니다."),
    E997("E997", "파라미터 부족 에러"),
    E998("E998", "데이터베이스 에러"),
    E999("E999", "시스템 에러");

    public static ResultCode E101;
    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
