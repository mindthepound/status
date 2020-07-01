let xport = {
	_fallbacktoCSV: true,
	toXLS: function(tableId, filename) {
		this._filename = (typeof filename == 'undefined') ? tableId : filename;

		//var ieVersion = this._getMsieVersion();
		//Fallback to CSV for IE & Edge
		if ((this._getMsieVersion() || this._isFirefox()) && this._fallbacktoCSV) {
			return this.toCSV(tableId);
		} else if (this._getMsieVersion() || this._isFirefox()) {
			alert("Not supported browser");
		}

		//Other Browser can download xls
		var htmltable = document.getElementById(tableId);
		var html = htmltable.outerHTML;

		this._downloadAnchor("data:application/vnd.ms-excel" + encodeURIComponent(html), 'xls');
	},
	toCSV: function(tableId, filename) {
		this._filename = (typeof filename === 'undefined') ? tableId : filename;
		// Generate our CSV string from out HTML Table
		let csv = this._tableToCSV(document.getElementById(tableId));
		// Create a CSV Blob
		let blob = new Blob(["\ufeff" + csv], { type: "text/csv;charset=utf-8;" });

		// Determine which approach to take for the download
		if (navigator.msSaveOrOpenBlob) {
			// Works for Internet Explorer and Microsoft Edge
			navigator.msSaveOrOpenBlob(blob, this._filename + ".csv");
		} else {
			this._downloadAnchor(URL.createObjectURL(blob), 'csv');
		}
	},
	toCSVbySlice: function(csv, filename) {
		this._filename = (typeof filename === 'undefined') ? '제목 없음' : filename;

		// Create a CSV Blob
		let blob = new Blob(["\ufeff" + csv], { type: "text/csv;charset=utf-8;" });

		// Determine which approach to take for the download
		if (navigator.msSaveOrOpenBlob) {
			// Works for Internet Explorer and Microsoft Edge
			navigator.msSaveOrOpenBlob(blob, this._filename + ".csv");
		} else {
			this._downloadAnchor(URL.createObjectURL(blob), 'csv');
		}
	},
	_getMsieVersion: function() {
		let ua = window.navigator.userAgent;

		let msie = ua.indexOf("MSIE ");
		if (msie > 0) {
			// IE 10 or older => return version number
			return parseInt(ua.substring(msie + 5, ua.indexOf(".", msie)), 10);
		}

		let trident = ua.indexOf("Trident/");
		if (trident > 0) {
			// IE 11 => return version number
			let rv = ua.indexOf("rv:");
			return parseInt(ua.substring(rv + 3, ua.indexOf(".", rv)), 10);
		}

		let edge = ua.indexOf("Edge/");
		if (edge > 0) {
			// Edge (IE 12+) => return version number
			return parseInt(ua.substring(edge + 5, ua.indexOf(".", edge)), 10);
		}

		// other browser
		return false;
	},
	_isFirefox: function(){
		if (navigator.userAgent.indexOf("Firefox") > 0) {
			return 1;
		}

		return 0;
	},
	_downloadAnchor: function(content, ext) {
		let anchor = document.createElement("a");
		anchor.style = "display:none !important";
		anchor.id = "downloadanchor";
		document.body.appendChild(anchor);

		// If the [download] attribute is supported, try to use it

		if ("download" in anchor) {
			anchor.download = this._filename + "." + ext;
		}
		anchor.href = content;
		anchor.click();
		anchor.remove();
	},
	_tableToCSV: function(table) {
		// We'll be co-opting `slice` to create arrays
		let slice = Array.prototype.slice;

		let beforeRow
			, rowspan = {};
		return slice
			.call(table.rows)
			.map(function(row) {
				return slice
					.call(row.cells)
					.map(function(cell, idx) {
						let preText = '';
						if (rowspan[idx] > 1) preText = `"${beforeRow.cells[idx].textContent}",`;
						return (preText + '"t"').replace("t", cell.textContent);
					})
					.join(",");
			})
			.join("\r\n");
	},
	_tableToCSVWithRowSpan: function (table) {
		const { rows } = table;

		let beforeRow
			, beforeRowSpan;
		let csv = '';
		for (let rowIdx = 0; rowIdx < rows.length; rowIdx++) {
			const row = rows[rowIdx];

			const { cells } = row;
			for (let cellIdx = 0; cellIdx < cells.length; cellIdx++) {
				const cell = cells[cellIdx];
				if (cell.rowSpan > 1) {
					beforeRow = row;

					beforeRowSpan = Array.from(beforeRow.cells).map(function (cell) { return cell.rowSpan; });
				}

				let isPrev = false;
				if (beforeRow !== row && beforeRow && beforeRowSpan[cellIdx] > 1) {
					isPrev = true;
					beforeRowSpan[cellIdx]--;
				}

				csv += (isPrev? `,`: '') + `"${cell.textContent}"` + (cellIdx < cells.length - 1? ',': '\r\n');
			}
		}
		return csv;
	}
};
