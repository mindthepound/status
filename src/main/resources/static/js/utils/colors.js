const rgbToHex = (r, g, b) => '#' + [r, g, b]
	.map(x => x.toString(16).padStart(2, '0')).join('');

const replaceallRgbToHex = (str) => str.replace(
	/\brgb\s*\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*\)/g,
	function($0, $1, $2, $3) {
		return "#" + ("0"+Number($1).toString(16)).substr(-2) + ("0"+Number($2).toString(16)).substr(-2) + ("0"+Number($3).toString(16)).substr(-2);
	});
