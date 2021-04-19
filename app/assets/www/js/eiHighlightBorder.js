

function setSourceElementOnEI() {
	var bodyInnerElement = document.getElementsByTagName('body')[0].innerHTML;
	document.getElementsByTagName('body')[0].innerHTML = bodyInnerElement +
	'<style type="text/css">.eiTagBorderDS {border: 1px dashed rgba(245,0,0,.4);}.eiTagBorderDS:hover {cursor: pointer; box-shadow: 0 2px 7px rgba(0,0,0,.4);}</style>' +
	'';

	var elmEIDS = document.getElementsByTagName("*");
	for(var i = 0, all = elmEIDS.length; i < all; i++){
	    elmEIDS[i].classList.add('eiTagBorderDS');
	}
}

function setSourceElementOffEI() {
	var elmEIDS = document.getElementsByTagName("*");
	for(var i = 0, all = elmEIDS.length; i < all; i++){
	    elmEIDS[i].classList.remove('eiTagBorderDS');
	}
}
// Powered by DoctorSteep - from project ElementInspector