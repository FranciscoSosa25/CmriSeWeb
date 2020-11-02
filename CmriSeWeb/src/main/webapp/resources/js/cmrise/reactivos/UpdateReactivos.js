function argsValidationFailed(){
	          console.log('Comienza argsValidationFailed()');
        	  $(".ui-message-error").delay(1800).fadeOut(500);
              $(".ui-messages-error").delay(1800).fadeOut(500);
              $(".ui-messages-info").delay(1800).fadeOut(500);
              $(".ui-messages-warn").delay(1800).fadeOut(500);
              console.log('Sale argsValidationFailed()');
          }
     
	function handleUpdateRequest(xhr, status, args){
		 const fileInput = document.getElementById('UpdateReactivosForm:fileImagenSen'); 
			if(args.validationFailed|| !args.updateIn) {
			 argsValidationFailed(); 
	      }else{
	    	  console.log('Comienza Procesar ImagenSen');
	    	  console.log('Comienza Finaliza ImagenSen');
	      }
	}

	function handlePresentationRequest(xhr, status, args){
		console.log("Entra handlePresentationRequest");
		 if(args.validationFailed|| !args.updateIn) {
			 argsValidationFailed(); 
	      }
		console.log("Sale handlePresentationRequest");
	}


	function init() {   
		
		console.log("mydiagram");
	    var $ = go.GraphObject.make;
	    myDiagram = $(go.Diagram, "myDiagramDiv");
	    myDiagram.toolManager.mouseDownTools.insertAt(3, new GeometryReshapingTool());
	    myDiagram.allowResize = true;
	    myDiagram.allowReshape = true;
	    myDiagram.allowRotate = true;
	    updateAllAdornments();
	    
	    myDiagram.nodeTemplateMap.add("PolygonDrawing",         
	      $(go.Node,         
	        { locationSpot: go.Spot.Center },  // to support rotation about the center         
	        new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
	       // new PathFigure(),
	        {
	          selectionAdorned: true, selectionObjectName: "SHAPE",
	          selectionAdornmentTemplate:  // custom selection adornment: a red rectangle
	            $(go.Adornment, "Auto",
	              $(go.Shape, { stroke: "red", fill: null }),
	              $(go.Placeholder, { margin: -1 }))
	        },
	        { resizable: true, resizeObjectName: "SHAPE" },
	        { rotatable: true, rotateObjectName: "SHAPE" },
	        { reshapable: true },  // GeometryReshapingTool assumes nonexistent Part.reshapeObjectName would be "SHAPE"
	        $(go.Shape,
	          { name: "SHAPE", fill: "red", strokeWidth: 1 },
	          new go.Binding("desiredSize", "size", go.Size.parse).makeTwoWay(go.Size.stringify),
	          new go.Binding("angle").makeTwoWay(),
	          new go.Binding("geometryString", "geometry").makeTwoWay(),
	          new go.Binding("fill"),
	          new go.Binding("stroke"),
	          new go.Binding("strokeWidth"))
	      ));
	    
	    // create polygon drawing tool for myDiagram, defined in PolygonDrawingTool.js         
	    var tool = new PolygonDrawingTool();
	  
	    // provide the default JavaScript object for a new polygon in the model
	    tool.archetypePartData =         
	      { fill: "rgba(255,0,0,0.3)", stroke: "red", strokeWidth: 1.5, category: "PolygonDrawing" };         
	    tool.isPolygon = true;  // for a polyline drawing tool set this property to false
	  
	    // install as first mouse-down-tool         
	    myDiagram.toolManager.mouseDownTools.insertAt(0, tool);
	    load();  // load a simple diagram from the textarea         
	    
	    var tool = myDiagram.toolManager.mouseDownTools.elt(0);         
	    tool.isEnabled = true;         
	    tool.isPolygon = true;         
	    tool.archetypePartData.fill = (polygon ? "rgba(255,0,0,0.3)" : "red");         
	    tool.temporaryShape.fill = (polygon ? "rgba(255,0,0,0.3)" : "red");         
	  }
	//TERMINA init

	//---------------------- COMIENZAN funciones


	  function mode(draw, polygon) {         
	    // assume PolygonDrawingTool is the first tool in the mouse-down-tools list         
	    var tool = myDiagram.toolManager.mouseDownTools.elt(0);         
	    tool.isEnabled = draw;         
	    tool.isPolygon = polygon;         
	    tool.archetypePartData.fill = (polygon ? "rgba(255,0,0,0.25)" : "red");         
	    tool.temporaryShape.fill = (polygon ? "rgba(255,0,0,0.3)" : "red");         
	  }

	  // this command ends the PolygonDrawingTool         
	  function finish(commit) {         
	    var tool = myDiagram.currentTool;         
	    if (commit && tool instanceof PolygonDrawingTool) {         
	      var lastInput = myDiagram.lastInput;         
	      if (lastInput.event instanceof window.MouseEvent) tool.removeLastPoint();  // remove point from last mouse-down         
	      tool.finishShape();         
	    } else {         
	      tool.doCancel();         
	    }         
	  }

	  // this command removes the last clicked point from the temporary Shape         
	  function undo() {         
	    var tool = myDiagram.currentTool;         
	    if (tool instanceof PolygonDrawingTool) {         
	      var lastInput = myDiagram.lastInput;         
	      if (lastInput.event instanceof window.MouseEvent) tool.removeLastPoint();  // remove point from last mouse-down         
	      tool.undo();         
	    }         
	  }
	  
	  function updateAllAdornments() {  // called after checkboxes change Diagram.allow...         
	    myDiagram.selection.each(function(p) { p.updateAdornments(); });         
	  }
	  
	  // save a model to and load a model from Json text, displayed below the Diagram         
	  function save() {         
	    var str = '{ "position": "' + go.Point.stringify(myDiagram.position) + '",\n  "model": ' + myDiagram.model.toJson() + ' }';         
	    document.getElementById("mySavedDiagram").value = str;         
	  }
	  
	  function load() {         
	    var str = document.getElementById("mySavedDiagram").value;         
	    try {         
	      var json = JSON.parse(str);         
	      myDiagram.initialPosition = go.Point.parse(json.position || "0 0");         
	      myDiagram.model = go.Model.fromJson(json.model);         
	      myDiagram.model.undoManager.isEnabled = true;         
	    } catch (ex) {         
	      alert(ex);         
	    }         
	  }
	   


 
$(document).ready(function() {
	 console.log('Hola');	 
	init(); 
	  });
  