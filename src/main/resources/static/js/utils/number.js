numberWithCommas = (num, defaultReturn = '') => {
	if (!num) return defaultReturn;

	return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
};

numberWithoutCommas = (num, defaultReturn = '') => {
	if (!num) return defaultReturn;

	num = num.replace(/\,/g, '');
	return num;
};

percentRound = (num, dot) => {
  return Math.round(num * 100 * dot) / dot;
};

/**
 * 소수점 짜리까지 반올림
 * @param num 숫자
 * @param dot 반올림하기위한 자릿수 (예, 소수점 두째자리: 100.0 / 소수점 세째자리: 1000.0)
 * @returns {number}
 */
roundDot = (num, dot) => {
	return Math.round(num * dot) / dot;
};

sortByKey = (arr, key, desc=false) => {
	arr.sort(function (before, after) {
		const beforeDate = new Date(before[key])
			, afterDate = new Date(after[key]);

		if (beforeDate > afterDate) return 1;
		else if (beforeDate < afterDate) return -1;
		return 0;
	});

  return desc? arr: arr.reverse();
};