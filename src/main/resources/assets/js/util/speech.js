$(document).ready(function(){
	

	/*var final_span = $('#final_span');
	var interim_span = $('#interim_span');*/
	var nlp_text = $('#nlp_text');
	var final_transcript = "";
	
	$('#startBtn').bind('click', function(event) {
		BtnHandle();
		
	});
	 
	var recognition = null;
	if (!('webkitSpeechRecognition' in window)	) {
			console.log("no webkitSpeechRecognition in window");
		}
		else{
			console.log(" webkitSpeechRecognition in window");
			recognition = new webkitSpeechRecognition();
			recognition.continuous = true;
			recognition.interimResults = true;
			recognition.land = "en-US";
			
			recognition.onresult = function(event){
				
				var interim_transcript = ''; 
		
				for (var i = event.resultIndex; i < event.results.length; ++i) {
					if (event.results[i].isFinal) {
						final_transcript += event.results[i][0].transcript;
						
					} else {
						interim_transcript += event.results[i][0].transcript;
					}
					
				}
				console.log(final_transcript);
				
				final_transcript = capitalize(final_transcript);
				nlp_text.value = final_transcript;
				 /*final_span.html(final_transcript+".");
				interim_span.html(linebreak(interim_transcript));*/
				

				};

			};
			recognition.onerror = function(event){ 
				console.log('error');
				console.log(event);
			};
			recognition.onend = function(){
				console.log('stopped!');
			};
			
	
	
	function BtnHandle(){
		final_transcript = "";
		recognition.stop();
		recognition.start();
		
	};
	

	var two_line = /\n\n/g;
	var one_line = /\n/g;
	function linebreak(s) {
		return s.replace(two_line, '<p></p>').replace(one_line, '<br>');
	}

	var first_char = /\S/;
	function capitalize(s) {
		return s.replace(first_char, function(m) {
			return m.toUpperCase();
		});
	}

	
});