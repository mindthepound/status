/**
 * read text from file
 */
function getTextFile(file) {
	let rawFile = new XMLHttpRequest();
	rawFile.open("GET", file, false);
	rawFile.onreadystatechange = function () {
		if(rawFile.readyState === 4)
		{
			if(rawFile.status === 200 || rawFile.status == 0)
			{
				let allText = rawFile.responseText;
				alert(allText);
			}
		}
	};
	rawFile.send(null);

	return rawFile;
}

/**
 * read text from URL location
 */
function getText(url){
	let request = new XMLHttpRequest();
	request.open('GET', 'https://s3-ap-northeast-1.amazonaws.com/spin-settings/version_android', true);
	request.send(null);
	request.onreadystatechange = function () {
		if (request.readyState === 4 && request.status === 200) {
			let type = request.getResponseHeader('Content-Type');
			if (type.indexOf("text") !== 1) {
				return request.responseText;
			}
		}
	}
}