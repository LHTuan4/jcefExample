var searchText = 'zing';
if (window.location.href.toString().search(searchText.toString()) < 0 && window.location.href.toString().search("google.com") >=0 )
	{
		document.getElementById('lst-ib').value = searchText;
		document.getElementsByClassName('tsf')[0].submit(); 
	}

