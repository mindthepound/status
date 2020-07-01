const OS_NAME = {
  unknown: 'Unknown OS',
  Windows: 'Windows',
  Mac: 'Mac',
  UNIX: 'UNIX',
  Linux: 'Linux'
};

const NO_IMAGE = 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ0LjA1NDY4NzUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+';

const CALENDAR = {
    ONE_DAY: {
        hours: 24,
        minutes: 60 * 24,
        seconds: 60 * 60 * 24
    },
    ONE_HOUR: {
        hour: 1,
        minutes: 60,
        seconds: 60 * 60
    },
    ONE_MINUTE: {
        minute: 1,
        seconds: 60
    },
    ONE_SECOND: 1
};

const KEY_CODE = {
    backspace: 8,
    enter: 13
};

const SERVER_STATUS = {
    none: 0,
    develop: 1,
    production: 2
};

// region NOTICE
const NOTICE_TYPE = {
    main: 'main',
    story: 'story'
};
// endregion

// region AD
const AD_CAMPAIGN_TYPE = {
    close: 'close',
    coinbox: 'coinbox',
    drawerad1: 'drawerad1',
    drawerad2: 'drawerad2',
    drawerad3: 'drawerad3',
    drawerad4: 'drawerad4',
    drawerad5: 'drawerad5'
};

const AD_TYPE = {
    cpm: 'cpm',
    cpc: 'cpc',
    cpp: 'cpp'
};

const ID_POSTFIX = {
    cpm: '_m',
    cpc: '_c',
    cpp: '_p'
};

const RESULT_CODE = {
    OK: 'OK',

    E001: 'E001',

    E801: 'E801',
    E802: 'E802',
    E803: 'E803',

    E997: 'E997',
    E998: 'E998',
    E999: 'E999'
};
// endregion

// region point
const POINT_CS_TYPE = {
    plus: 'PLUS',
    minus: 'MINUS'
};

const POINT_CS_SUB_TYPE = {
    plus: {
        TREASURE_BOX: '보물상자',
        INVITE: '친구초대',
        DISAPPEAR: '포인트 소멸',
        REWARD: '리워드',
        COUPON_REFUND: '쿠폰 환불',
        RAFFLE_ERROR: '뽑기 오류',
        GAME_REWARD: '게임 리워드',
        EVENT_GAME_REWARD: '이벤트 게임 리워드',
        CACHE_MIGRATION_PLUS: '캐시 이전 적립',
        EVENT: '이벤트',
        OTHER_PLUS: '기타 적립',
        TEST_PLUS: '테스트 목적 적립'
    },
    minus: {
        DUPLICATED_PROCESSING: '중복 지급',
        CACHE_MIGRATION_MINUS: '캐시 이전 차감',
        OTHER_MINUS: '기타 차감',
        TEST_MINUS: '테스트 목적 차감'
    }
};

const POINT_USED_TYPE = ['RAFFLE', 'SHOP', 'GAME_SHOP', 'HEART', 'GAME_CASH', 'TOOMICS'];
// endregion

// region coupon
const COUPON_STATUS = {
    used: 4006,
    expired: 4007
};
// endregion

// region prize
const PRIZE_CATEGORY_TYPE = {
    DEFAULT: 'DEFAULT',
    LANDING: 'LANDING',
    PROMOTION: 'PROMOTION'
};
const PRIZE_TYPE = {
	EVENT_IMAGE_URL: 'EVENT_IMAGE_URL', // 외부 뽑기
  DEFAULT: 'DEFAULT', // 프로모션
};
// endregion