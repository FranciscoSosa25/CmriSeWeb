/**
 * 
 */
const imagenCorrelacionada = "Imagen Correlacionada"; 
let tipoPregunta; 
let fileImagenCorID; 
let graphicImageImgCorID;
let imgWidthImgCorID; 
let imgHeightImgCorID; 
let $goJs; 
let imgCorDivID; 
let myDiagram; 
let agregarNodoImgCorID; 
let diagram; 
let myModel; 
let nodos=[];
let respuestasCorrelacionadas=[]; 
let agregarRespReactBtn; 
let preguntaID; 
let preguntasImgCorDivID; 
let coordinatesImgCorId; 

function onstartUFTA(){
	console.log("Comienza onstartUFTA"); 
	tipoPregunta = document.getElementById('UpdateReactivosForm:tipoPreguntaImgCor'); 
    if(null==tipoPregunta){
	   return; 
    }
    
    coordinatesImgCorId = document.getElementById('UpdateReactivosForm:coordinatesImgCor'); 
    console.log(diagram); 
    console.log(diagram.model); 
    console.log(diagram.model.toJson()); 
    coordinatesImgCorId.value = diagram.model.toJson(); 
    console.log("Finaliza onstartUFTA"); 
}

function RespuestaImgCor(pNumero,pRespuesta){
	this.numero = pNumero; 
	this.respuesta = pRespuesta; 
}

$(document).ready(function() {
	 console.log("Comienza Ready"); 
	 console.log("Finaliza Ready");
}); 

document.addEventListener('DOMContentLoaded', () => {
   console.log(imagenCorrelacionada+' Comienza DOMContentLoaded');
   /**
   $goJs = go.GraphObject.make;
   diagram = new go.Diagram("UpdateReactivosForm:imgCorDiv");
   **/
   $goJs = go.GraphObject.make;
   diagram = $goJs(go.Diagram, "UpdateReactivosForm:imgCorDiv",
		         { // enable Ctrl-Z to undo and Ctrl-Y to redo
		           "undoManager.isEnabled": true,
		           allowVerticalScroll:false, 
		           allowHorizontalScroll:false/*, 
		           isReadOnly:true*/
		         });

   tipoPregunta = document.getElementById('UpdateReactivosForm:tipoPreguntaImgCor'); 
   if(null==tipoPregunta){
	   return; 
   }
   fileImagenCorID = document.getElementById('UpdateReactivosForm:fileImagenCor');
   imgWidthImgCorID = document.getElementById('UpdateReactivosForm:imgWidthImgCor'); 
   imgHeightImgCorID = document.getElementById('UpdateReactivosForm:imgHeightImgCor'); 
   imgCorDivID = document.getElementById('UpdateReactivosForm:imgCorDiv');
   agregarNodoImgCorID = document.getElementById('UpdateReactivosForm:agregarNodoImgCor');
   agregarRespReactBtn = document.getElementById('UpdateReactivosForm:agregarRespReact');
   preguntaID = document.getElementById('UpdateReactivosForm:pregunta');
   preguntasImgCorDiv = document.getElementById('preguntasImgCorDiv');
   
   fileImagenCorID.addEventListener('change', (e) => {
	    console.log("Comienza fileInput change"); 
	    const file = e.target.files[0]; 
	    const fileReader = new FileReader(); 
	    fileReader.readAsDataURL(file); 
	    fileReader.addEventListener('load',(e)=>{
	      	 var img = new Image();
	    	 img.setAttribute('src', e.target.result); 
	    	 img.addEventListener('load',(e)=>{
	    	    console.log(img.width); 
	    	    console.log(img.height); 
	    	    imgWidthImgCorID.value = img.width; 
	    	    imgHeightImgCorID.value = img.height; 
	    	    imgCorDivID.style.width = img.width+"px"; 
	    	    imgCorDivID.style.height = img.height+"px"; 
	    	    
	    	 }); 
	    	
	    }); 
	   
	   console.log("Finaliza fileInput change"); 	    
	});
   
   
   /*
   agregarRespReactBtn.addEventListener('click', (e) => {
	   console.log("Entra agregarRespReactBtn click"); 
	   console.log(preguntaID); 
	   var clone = preguntaID.cloneNode(true); 
	   preguntasImgCorDiv.appendChild(clone); 
	   console.log("Sale agregarRespReactBtn click"); 
   }); 
   */
   graphicImageImgCorID = document.getElementById('graphicImageImgCorID');
   if(graphicImageImgCorID.src=="data:;base64,"){
	   return; 
   }
    
   var img = new Image();
   img.src = graphicImageImgCorID.src; 
   img.addEventListener('load',(e)=>{
  	     imgWidthImgCorID.value = img.width; 
	     imgHeightImgCorID.value = img.height; 
	     imgCorDivID = diagram.div; 
	     imgCorDivID.style.backgroundImage = 'url("'+e.target.src+'")'; 
	     imgCorDivID.style.width = imgWidthImgCorID.value+"px"; 
	     imgCorDivID.style.height = imgHeightImgCorID.value+"px"; 
	     
	     /*** Bug Alteracion de Posicionamiento **/
	     coordinatesImgCorId  = document.getElementById('UpdateReactivosForm:coordinatesImgCor');
	     
	     diagram.linkTemplate =
	  	    $goJs(go.Link,
	  	      $goJs(go.Shape,
	  	        new go.Binding("stroke", "color"),  // shape.stroke = data.color
	  	        new go.Binding("strokeWidth", "thick")),  // shape.strokeWidth = data.thick
	  	      $goJs(go.Shape,
	  	        { toArrow: "OpenTriangle", fill: null },
	  	        new go.Binding("stroke", "color"),  // shape.stroke = data.color
	  	        new go.Binding("strokeWidth", "thick"))  // shape.strokeWidth = data.thick
	  	    );

	   diagram.nodeTemplate =
	  	  $goJs(go.Node, "Auto", { // the Node.location point will be at the center of each node
	  	      locationSpot: go.Spot.Center
	  	       },new go.Binding("location", "loc").makeTwoWay(),
	  			  $goJs(go.Shape,
	  	        new go.Binding("figure", "fig"),
	  	        new go.Binding("fill", "color"),
	  	        new go.Binding("stroke", "color"),
	  	        new go.Binding("strokeWidth", "thick")
	  			  ),
	  	        $goJs(go.TextBlock,
	  	        { margin: 5 },new go.Binding("text", "say"))
	  	    );
	     
	     diagram.model = go.Model.fromJson(coordinatesImgCorId.value);
	     
    }); 
   
   
 
   console.log(imagenCorrelacionada+' Finaliza DOMContentLoaded');
}); 

function oncompleteFileUploadImgCorBkp(){
	 console.log(imagenCorrelacionada+' Comienza oncompleteFileUploadImgCor');
     graphicImageImgCorID = document.getElementById("graphicImageImgCorID"); 
     agregarNodoImgCorID = document.getElementById('UpdateReactivosForm:agregarNodoImgCor');
     
	    imgCorDivID.style.width = imgWidthImgCorID.value+"px"; 
	    imgCorDivID.style.height = imgHeightImgCorID.value+"px"; 
	    
	    diagram = new go.Diagram("UpdateReactivosForm:imgCorDiv");
	    diagram.model = new go.GraphLinksModel(
	      [{ key: "Hello" },   // two node data, in an Array
	       { key: "World!" }],
	      [{ from: "Hello", to: "World!"}]  // one link data, in an Array
	    );
	  
	  
	   imgCorDivID = diagram.div; 
	   imgCorDivID.style.width = imgWidthImgCorID.value+"px"; 
	   imgCorDivID.style.height = imgHeightImgCorID.value+"px"; 
	
	     var img = new Image();
	     img.src = graphicImageImgCorID.src; 
	     img.addEventListener('load',(e)=>{
	    	 imgCorDivID.style.backgroundImage = 'url("'+e.target.src+'")'; 
	      }); 
	   
	   
	     var node = new go.Node(go.Panel.Auto);
	     var shape = new go.Shape();
	     shape.figure = "RoundedRectangle";
	     shape.fill = "lightblue";
	     node.add(shape);
	     var textblock = new go.TextBlock();
	     textblock.text = "A";
	     textblock.margin = 5;
	     node.add(textblock);
	     diagram.add(node);
	     
	        if(null!==agregarNodoImgCorID){
	        agregarNodoImgCorID.addEventListener('click', (e) => {
	    	   	console.log("Comienza agregarNodoImgCorID click"); 
	    	    var node = new go.Node(go.Panel.Auto);
	    	    var shape = new go.Shape();
	    	    shape.figure = "RoundedRectangle";
	    	    shape.fill = "lightblue";
	    	    node.add(shape);
	    	    var textblock = new go.TextBlock();
	    	    textblock.text = "A";
	    	    textblock.margin = 5;
	    	    node.add(textblock);
	    	    diagram.add(node);
	    	   	console.log("Finaliza agregarNodoImgCorID click");
	    	   }); 
	        }
	     
	     
	   
	   console.log(imagenCorrelacionada+' Finaliza oncompleteFileUploadImgCor');
}


function oncompleteFileUploadImgCor(){
	 console.log(imagenCorrelacionada+' Comienza oncompleteFileUploadImgCor');
     graphicImageImgCorID = document.getElementById("graphicImageImgCorID"); 
     agregarNodoImgCorID = document.getElementById('UpdateReactivosForm:agregarNodoImgCor');
     imgWidthImgCorID = document.getElementById('UpdateReactivosForm:imgWidthImgCor'); 
     imgHeightImgCorID = document.getElementById('UpdateReactivosForm:imgHeightImgCor'); 
     
     $goJs = go.GraphObject.make;
     diagram = $goJs(go.Diagram, "UpdateReactivosForm:imgCorDiv",
		         { // enable Ctrl-Z to undo and Ctrl-Y to redo
		           "undoManager.isEnabled": true,
		           allowVerticalScroll:false, 
		           allowHorizontalScroll:false/*, 
		           isReadOnly:true*/
		         });
    
     
    /**
 	diagram.nodeTemplate =
		    $goJs(go.Node, "Auto",
		      $goJs(go.Shape, "RoundedRectangle",
		        { fill: "white" },
		        new go.Binding("fill", "color")),  // shape.fill = data.color
		      $goJs(go.TextBlock,
		        { margin: 5 },
		        new go.Binding("text", "key"))  // textblock.text = data.key
		    );

		  diagram.linkTemplate =
		    $goJs(go.Link,
		      $goJs(go.Shape,
		        new go.Binding("stroke", "color"),  // shape.stroke = data.color
		        new go.Binding("strokeWidth", "thick")),  // shape.strokeWidth = data.thick
		      $goJs(go.Shape,
		        { toArrow: "OpenTriangle", fill: null },
		        new go.Binding("stroke", "color"),  // shape.stroke = data.color
		        new go.Binding("strokeWidth", "thick"))  // shape.strokeWidth = data.thick
		    );

		  diagram.nodeTemplate =
		    $goJs(go.Node, "Auto",
		      new go.Binding("location", "loc"),  // get the Node.location from the data.loc value
		      $goJs(go.Shape, "RoundedRectangle",
		        { fill: "white" },
		        new go.Binding("fill", "color")),
		      $goJs(go.TextBlock,
		        { margin: 5 },
		        new go.Binding("text", "key"))
		    );

		  var nodeDataArray = [
	 		    // for each node specify the location using Point values
	 		    { key: "Alpha", color: "lightblue", loc: new go.Point(0, 0) },
	 		    { key: "Beta", color: "pink", loc: new go.Point(100, 50) }
	 		  ];
	 		  
	 		  var linkDataArray = [
	 		    { from: "Alpha", to: "Beta" }
	 		  ];
	 		  
	 		  diagram.model = new go.GraphLinksModel(nodeDataArray, linkDataArray);
	 **/		  
	 		  
    
	
     var img = new Image();
     img.src = graphicImageImgCorID.src; 
     img.addEventListener('load',(e)=>{
    	 imgWidthImgCorID.value = img.width; 
  	     imgHeightImgCorID.value = img.height; 
  	   
  	     imgCorDivID = diagram.div; 
  	     imgCorDivID.style.backgroundImage = 'url("'+e.target.src+'")'; 
  	     imgCorDivID.style.width = imgWidthImgCorID.value+"px"; 
  	     imgCorDivID.style.height = imgHeightImgCorID.value+"px"; 
  	     
  	   diagram.grid.visible = true;  
  	   diagram.startTransaction("change Layout");
       var lay = diagram.layout;
       lay.cellSize = go.Size.parse("500 500");
       lay.alignment = go.GridLayout.Location;
       diagram.commitTransaction("change Layout");
       
      }); 
	 
     
     
     if(null!=agregarNodoImgCorID){
    	 agregarNodoImgCorID.addEventListener('click', (e) => {
    	 	   	console.log("Comienza agregarNodoImgCorID click"); 
    	 	   var node1 =
    	 		  $goJs(go.Node, "Auto",
    	 				 $goJs(go.Shape,
    	 		        { figure: "RoundedRectangle",
    	 		          fill: "lightblue" }),
    	 		         $goJs(go.TextBlock,
    	 		        { text: "Alpha",
    	 		          margin: 5 })
    	 		    )
    	 		  diagram.add(node1);

    	 		  var node2 =
    	 			 $goJs(go.Node, "Auto",
    	 		         $goJs(go.TextBlock,
    	 		        { text: "",
    	 		          margin: 5 })
    	 		    );
    	 		  diagram.add(node2);
    	 		  
    	 		 diagram.add(
    	 				$goJs(go.Link,
    	 			      { fromNode: node1, toNode: node2 },
    	 			     $goJs(go.Shape)
    	 			    ));
    	 	   	
    	 		console.log(diagram.model); 
    	 		console.log(diagram.model.toJson()); 
    	  		
    	 	   	console.log("Finaliza agregarNodoImgCorID click");
    	 	   }); 
     }
     
     
     /*
     myModel = $goJs(go.Model);
     // for each object in this Array, the Diagram creates a Node to represent it
     myModel.nodeDataArray = [
       { key: "Alpha" },
       { key: "Beta" },
       { key: "Gamma" }
     ];
     
     diagram.model = myModel;
     */
     
     console.log(nodos); 
     
     console.log(imagenCorrelacionada+' Finaliza oncompleteFileUploadImgCor');
}

function handleAgregarRespuestaRequest(xhr, status, args){
	console.log('Comienza handleAgregarRespuestaRequest'); 
	console.log(xhr); 
	console.log(status); 
	console.log(args); 
	console.log('Comienza handleAgregarRespuestaRequest'); 
}

function handleAgregarRespuestaCorrRequest(xhr, status, args){
	console.log('Comienza handleAgregarRespuestaCorrRequest'); 
	console.log(nodos);

	if(args.validationFailed||null==args.nodo) {
	 }else{
	  //diagram.startTransaction();
	  nodos.push(args.nodo); 
	 
	  diagram.linkTemplate =
		    $goJs(go.Link,
		      $goJs(go.Shape,
		        new go.Binding("stroke", "color"),  // shape.stroke = data.color
		        new go.Binding("strokeWidth", "thick")),  // shape.strokeWidth = data.thick
		      $goJs(go.Shape,
		        { toArrow: "OpenTriangle", fill: null },
		        new go.Binding("stroke", "color"),  // shape.stroke = data.color
		        new go.Binding("strokeWidth", "thick"))  // shape.strokeWidth = data.thick
		    );
	 
	  diagram.nodeTemplate =
		  $goJs(go.Node, "Auto", { // the Node.location point will be at the center of each node
		      locationSpot: go.Spot.Center
		       },new go.Binding("location", "loc").makeTwoWay(),
				  $goJs(go.Shape,
		        new go.Binding("figure", "fig"),
		        new go.Binding("fill", "color"),
		        new go.Binding("stroke", "color"),
		        new go.Binding("strokeWidth", "thick")
				  ),
		        $goJs(go.TextBlock,
		        { margin: 5 },new go.Binding("text", "say"))
		    );
	  
	  diagram.startTransaction("Add State");
	  diagram.startTransaction("make new node");
	  let nodoFrom = {id:"from"+args.nodo, key: "from"+args.nodo,say:args.nodo,fig: "RoundedRectangle",color: "lightblue",loc: new go.Point(0, 0)}; 
	  let nodoTo =  {id:"to"+args.nodo, key:"to"+args.nodo,say:"",fig: "PlusLine",stroke: "yellow",color: "yellow",loc: new go.Point(100, 50),thick:2}; 
	  diagram.model.addNodeData(nodoFrom);
	  diagram.model.addNodeData(nodoTo);
	 
	  
	  let linkdata = {
		          from: diagram.model.getKeyForNodeData(nodoFrom),  // or just: fromData.id
		          to: diagram.model.getKeyForNodeData(nodoTo),
		          color:"yellow",
		          thick: 2
		        };
	  
	  diagram.model.addLinkData(linkdata);
	  diagram.commitTransaction("make new node");
	  
      /**
	  var node1 =
 		  $goJs(go.Node, "Auto",
 				 $goJs(go.Shape,
 		        { figure: "RoundedRectangle",
 		          fill: "lightblue" }),
 		         $goJs(go.TextBlock,
 		        { text: args.nodo,
 		          margin: 5 })
 		    ); 
	  
 		  diagram.model.addNodeData(node1);
    
 		  var node2 =
 			 $goJs(go.Node, "Auto",
 	 				 $goJs(go.Shape,
 	 		        { figure: "PlusLine",
 	 		          stroke: "yellow" }),
 	 		         $goJs(go.TextBlock,
 	 		        { text: "",
 	 		          margin: 5 })
 	 		    ); 
 		  
 		  diagram.model.addNodeData(node2);
 		  
 		

 		let linkdata = {
 		          from: diagram.model.getKeyForNodeData(node1),  // or just: fromData.id
 		          to: diagram.model.getKeyForNodeData(node2),
 		          text: "transition"
 		        };
 		
 		let fromNode1ToNode2 = $goJs(go.Link,
               { fromNode: nodoFrom, toNode: nodoTo },
              $goJs(go.Shape,{stroke:"yellow"}),
              $goJs(go.Shape,{ toArrow: "OpenTriangle", fill: null },{stroke:"yellow"})
               ); 
	  
 		  
 		  
 		 diagram.model.addLinkData(linkdata);
 		 //diagram.commitTransaction("added"+args.nodo);
 		 **/
 		 diagram.commitTransaction("Add State"); 
	 }
	 
	console.log(xhr); 
	console.log(status); 
	console.log(args); 
	console.log(nodos);
	
	
	
	console.log('Finaliza handleAgregarRespuestaCorrRequest');
}
