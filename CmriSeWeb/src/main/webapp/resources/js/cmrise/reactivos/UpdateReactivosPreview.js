/**
 * 
 */

var contadorClicks = 0;
		var puntoCreado = false;
		function init(){
			var divArea = document.getElementById('myDiagramDiv');
			divArea.onclick = function() {
			    addShape();
			};

		}
		
		function addShape(){	
			contadorClicks = contadorClicks + 1;
			var numClicks = contadorClicks;
			
			if(!puntoCreado){
				puntoCreado = true				
		  		var $ = go.GraphObject.make;
				myDiagram = $(go.Diagram, "myDiagramDiv");
				myDiagram.add(
				    $(go.Part, "auto",
	    				$(go.Shape, "PlusLine", { width: 40, height: 40, margin: 4, fill: "red", stroke: "red", strokeWidth: 2 })
				    )
				);			
			}else{
				//alert('logica faltante');
			}
		}
		

		$(document).ready(function() {
			 console.log('Hola');	 
			init(); 
			  });		