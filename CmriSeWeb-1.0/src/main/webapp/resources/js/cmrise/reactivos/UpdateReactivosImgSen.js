/**
 *
 */
var canvasWidth;
var canvasHeight;
var perimeter = new Array();
var complete = false;
var canvas;
var ctx;
var coordinates;
var imgWidth;
var imgHeight;
var graphicImageImgSenID;
var DeshacerBtnID;
//var LimpiarBtnID;

document.addEventListener('DOMContentLoaded', () => {

    console.log('Comienza DOMContentLoaded');

    var tipoPregunta = document.getElementById('UpdateReactivosForm:tipoPregunta');

    imgWidth = document.getElementById('UpdateReactivosForm:imgWidth');
    imgHeight = document.getElementById('UpdateReactivosForm:imgHeight');
    canvas = document.getElementById("paintingCanvas");
    coordinates = document.getElementById('UpdateReactivosForm:coordinates');
    DeshacerBtnID = document.getElementById('UpdateReactivosForm:DeshacerBtn');

    //LimpiarBtnID = document.getElementById('UpdateReactivosForm:LimpiarBtn');

    if (null == tipoPregunta) {
        return;
    }

    const fileInput = document.getElementById('UpdateReactivosForm:fileImagenSen');

    graphicImageImgSenID = document.getElementById('graphicImageImgSenID');

    if (null == graphicImageImgSenID) {
        return;
    }

    /*graphicImageImgSenID.addEventListener('load', (e) => {
        console.log('Comienza onload UpdateReactivosForm:graphicImageImgSenID');

        console.log('Finaliza onload UpdateReactivosForm:graphicImageImgSenID');
    });*/

    /*graphicImageImgSenID.addEventListener('change', (e) => {
        console.log('Comienza onchange UpdateReactivosForm:graphicImageImgSenID');
        console.log(e);
        console.log('Finaliza onchange UpdateReactivosForm:graphicImageImgSenID');
    });*/

    fileInput.addEventListener('change', (e) => {

        console.log("Comienza fileInput change");

        const file = e.target.files[0];
        const fileReader = new FileReader();

        fileReader.readAsDataURL(file);

        fileReader.addEventListener('load', (e) => {

            var img = new Image();

            img.setAttribute('src', e.target.result);

            img.addEventListener('load', (e) => {

                imgWidth.value = img.width;
                imgHeight.value = img.height;

                canvasWidth = img.width;
                canvasHeight = img.height;
            });
        });

        console.log("Finaliza fileInput change");
    });


    /*canvas.addEventListener("mousemove", function (e) {
        var cRect = canvas.getBoundingClientRect(); // Gets CSS pos, and width/height
        var canvasX = Math.round(e.clientX - cRect.left); // Subtract the 'left' of the canvas
        var canvasY = Math.round(e.clientY - cRect.top); // from the X/Y positions to make
        document.getElementById('UpdateReactivosForm:coordinatesMouseMove').value = "X: " + canvasX + ", Y: " + canvasY;
    });*/

    //LimpiarBtnID.addEventListener("click", clear_canvas);

    if (graphicImageImgSenID.src == "data:;base64,") {
        return;
    }

    /*ctx = canvas.getContext("2d");
    ctx.drawImage(graphicImageImgSenID, 0, 0, canvas.width, canvas.height);*/

    if ('' == coordinates.value) {

        console.log('Sin Coordenadas');

        return;
    }

    console.log(coordinates.value);

    perimeter = JSON.parse(coordinates.value);
    console.log(perimeter);

    canvasWidth = imgWidth.value;
    canvasHeight = imgHeight.value;

    this.initImage();

    /*
    ctx.beginPath();
    ctx.moveTo(50,30);
    ctx.lineTo(75,55);
    ctx.lineTo(25,55);
    ctx.lineTo(50,30);
    ctx.fillStyle='blue';
    ctx.fill();
    */

    /*ctx.lineWidth = 1;
    ctx.strokeStyle = "white";
    ctx.lineCap = "square";
    ctx.beginPath();*/

    for (var i = 0; i < perimeter.length; i++) {
        if (i == 0) {
            ctx.moveTo(perimeter[i]['x'], perimeter[i]['y']);
        } else {
            ctx.lineTo(perimeter[i]['x'], perimeter[i]['y']);
        }
    }

    /*ctx.lineTo(perimeter[0]['x'], perimeter[0]['y']);
    ctx.closePath();
    ctx.fillStyle = 'rgba(255, 0, 0, 0.5)';
    ctx.fill();
    ctx.strokeStyle = 'blue';*/
    complete = true;
    /*ctx.stroke();*/

    console.log('Finaliza DOMContentLoaded');
});


function oncompleteFileUpload() {

    console.log('Comienza oncompleteFileUpload');

    /**** INICIA MOH ****/

    this.initImage();

    /**** FIN MOH ****/

    /*var graphicImageImgSenID = document.getElementById('graphicImageImgSenID');
    var canvas = document.getElementById("paintingCanvas");

    imgWidth = document.getElementById('UpdateReactivosForm:imgWidth');
    imgHeight = document.getElementById('UpdateReactivosForm:imgHeight');

    console.log("canvasWidth:" + canvasWidth);
    console.log("canvasHeight:" + canvasHeight);

    canvas.width = canvasWidth;
    canvas.height = canvasHeight;

    imgWidth.value = canvasWidth;
    imgHeight.value = canvasHeight;

    ctx = canvas.getContext("2d");
    ctx.drawImage(graphicImageImgSenID, 0, 0, canvas.width, canvas.height);

    var fileInput = document.getElementById('UpdateReactivosForm:fileImagenSen');

    fileInput.addEventListener('change', (e) => {

        const file = e.target.files[0];
        const fileReader = new FileReader();

        fileReader.readAsDataURL(file);

        fileReader.addEventListener('load', (e) => {

            var img = new Image();

            img.setAttribute('src', e.target.result);

            img.addEventListener('load', (e) => {

                canvasWidth = img.width;
                canvasHeight = img.height;

                canvas.style.width = img.width + "px";
                canvas.style.height = img.height + "px";
            });

        });
    });*/

    /*canvas.addEventListener("mousemove", function (e) {

        var cRect = canvas.getBoundingClientRect(); // Gets CSS pos, and width/height
        var canvasX = Math.round(e.clientX - cRect.left); // Subtract the 'left' of the canvas
        var canvasY = Math.round(e.clientY - cRect.top); // from the X/Y positions to make

        document.getElementById('UpdateReactivosForm:coordinatesMouseMove').value = "X: " + canvasX + ", Y: " + canvasY;
    });*/


    //perimeter = new Array();

    complete = false;

    document.getElementById('UpdateReactivosForm:coordinates').value = '';

    //start();

    console.log('Finaliza oncompleteFileUpload');
}


function line_intersects(p0, p1, p2, p3) {
    var s1_x, s1_y, s2_x, s2_y;
    s1_x = p1['x'] - p0['x'];
    s1_y = p1['y'] - p0['y'];
    s2_x = p3['x'] - p2['x'];
    s2_y = p3['y'] - p2['y'];

    var s, t;
    s = (-s1_y * (p0['x'] - p2['x']) + s1_x * (p0['y'] - p2['y'])) / (-s2_x * s1_y + s1_x * s2_y);
    t = (s2_x * (p0['y'] - p2['y']) - s2_y * (p0['x'] - p2['x'])) / (-s2_x * s1_y + s1_x * s2_y);

    if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
        // Collision detected
        return true;
    }
    return false; // No collision
}

function point(x, y) {
    ctx.fillStyle = "white";
    ctx.strokeStyle = "white";
    ctx.fillRect(x - 2, y - 2, 4, 4);
    ctx.moveTo(x, y);
}

function undo() {
    ctx = undefined;
    perimeter.pop();
    complete = false;
    start(true);
}

function clear_canvas() {
    console.log("Entra clear_canvas()");
    ctx = undefined;
    perimeter = new Array();
    complete = false;
    document.getElementById('UpdateReactivosForm:coordinates').value = '';

    graphicImageImgSenID = document.getElementById('graphicImageImgSenID');
    if (null !== graphicImageImgSenID) {
        console.log('null!==graphicImageImgSenID');
        var img = new Image();
        img.src = graphicImageImgSenID.src;
        img.onload = function () {
            ctx = canvas.getContext("2d");
            ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
        }
    }

    console.log("Sale clear_canvas()");
}

function draw(end) {

    ctx.lineWidth = 1;
    ctx.strokeStyle = "white";
    ctx.lineCap = "square";

    ctx.beginPath();

    for (var i = 0; i < perimeter.length; i++) {
        if (i == 0) {
            ctx.moveTo(perimeter[i]['x'], perimeter[i]['y']);
            end || point(perimeter[i]['x'], perimeter[i]['y']);
        } else {
            ctx.lineTo(perimeter[i]['x'], perimeter[i]['y']);
            end || point(perimeter[i]['x'], perimeter[i]['y']);
        }
    }
    if (end) {
        ctx.lineTo(perimeter[0]['x'], perimeter[0]['y']);
        ctx.closePath();
        ctx.fillStyle = 'rgba(255, 0, 0, 0.5)';
        ctx.fill();
        ctx.strokeStyle = 'blue';
        complete = true;
    }
    ctx.stroke();

    // print coordinates
    if (perimeter.length == 0) {
        document.getElementById('UpdateReactivosForm:coordinates').value = '';
    } else {
        document.getElementById('UpdateReactivosForm:coordinates').value = JSON.stringify(perimeter);
    }
}

function check_intersect(x, y) {
    if (perimeter.length < 4) {
        return false;
    }
    var p0 = new Array();
    var p1 = new Array();
    var p2 = new Array();
    var p3 = new Array();

    p2['x'] = perimeter[perimeter.length - 1]['x'];
    p2['y'] = perimeter[perimeter.length - 1]['y'];
    p3['x'] = x;
    p3['y'] = y;

    for (var i = 0; i < perimeter.length - 1; i++) {
        p0['x'] = perimeter[i]['x'];
        p0['y'] = perimeter[i]['y'];
        p1['x'] = perimeter[i + 1]['x'];
        p1['y'] = perimeter[i + 1]['y'];
        if (p1['x'] == p2['x'] && p1['y'] == p2['y']) {
            continue;
        }
        if (p0['x'] == p3['x'] && p0['y'] == p3['y']) {
            continue;
        }
        if (line_intersects(p0, p1, p2, p3) == true) {
            return true;
        }
    }
    return false;
}

function point_it(event) {
    canvas = document.getElementById("paintingCanvas");
    console.log(event);

    console.log("PRUEBA");

    if (complete) {
        alert('Actualmente existe un poligono');
        return false;
    }
    var rect, x, y;

    console.log(event.ctrlKey);
    console.log(event.which);
    console.log(event.button);

    if (event.ctrlKey || event.which === 3 || event.button === 2) {
        if (perimeter.length == 2) {
            alert('Se nececitan al menos 3 puntos para formar un poligono.');
            return false;
        }
        x = perimeter[0]['x'];
        y = perimeter[0]['y'];

        if (check_intersect(x, y)) {
            alert('La linea que se acaba de dibujar intersecta otra linea.');
            return false;
        }
        draw(true);
        alert('Poligono Cerrado');
        event.preventDefault();
        return false;
    } else {
        rect = canvas.getBoundingClientRect();
        console.log(canvas);
        console.log(rect);
        console.log('event.clientX:' + event.clientX);
        console.log('event.clientY:' + event.clientY);
        console.log('rect.left:' + rect.left);
        console.log('rect.top:' + rect.top);

        x = event.clientX - rect.left;
        y = event.clientY - rect.top;
        console.log('x:' + x);
        console.log('y:' + y);
        x = x.toFixed(5);
        y = y.toFixed(5);
        console.log('x:' + x);
        console.log('y:' + y);

        if (perimeter.length > 0 && x == perimeter[perimeter.length - 1]['x'] && y == perimeter[perimeter.length - 1]['y']) {
            // same point - double click
            return false;
        }
        if (check_intersect(x, y)) {
            alert('The line you are drowing intersect another line');
            return false;
        }
        perimeter.push({'x': x, 'y': y});
        draw(false);
        return false;
    }
}

function start(with_draw) {

    var img = new Image();

    img.src = canvas.getAttribute('data-imgsrc');

    img.onload = function () {

        ctx = canvas.getContext("2d");

        ctx.drawImage(img, 0, 0, canvas.width, canvas.height);

        if (with_draw == true) {

            draw(false);
        }
    }
}

function handleUpdateRequest(xhr, status, args) {
    console.log('Comienza handleUpdateRequest');
    console.log(xhr);
    console.log(status);
    console.log(args);

    graphicImageImgSenID = document.getElementById('graphicImageImgSenID');
    if (null !== graphicImageImgSenID) {
        console.log('null!=graphicImageImgSenID');

        imgWidth = document.getElementById('UpdateReactivosForm:imgWidth');
        imgHeight = document.getElementById('UpdateReactivosForm:imgHeight');
        canvas = document.getElementById("paintingCanvas");
        coordinates = document.getElementById('UpdateReactivosForm:coordinates');
        DeshacerBtnID = document.getElementById('UpdateReactivosForm:DeshacerBtn');
        //LimpiarBtnID = document.getElementById('UpdateReactivosForm:LimpiarBtn');

        graphicImageImgSenID.addEventListener('load', (e) => {
            console.log('Comienza onload UpdateReactivosForm:graphicImageImgSenID');

            console.log('Finaliza onload UpdateReactivosForm:graphicImageImgSenID');
        });

        graphicImageImgSenID.addEventListener('change', (e) => {
            console.log('Comienza onchange UpdateReactivosForm:graphicImageImgSenID');
            console.log(e);
            console.log('Finaliza onchange UpdateReactivosForm:graphicImageImgSenID');
        });

        //LimpiarBtnID.addEventListener("click", clear_canvas);

        if (graphicImageImgSenID.src == "data:;base64,") {
            PF('growlWV').renderMessage({
                    "summary": "Se nececita una",
                    "detail": "Imagen",
                    "severity": "error"
                }
            );
            return;
        }


        canvas.width = imgWidth.value;
        canvas.height = imgHeight.value;
        ctx = canvas.getContext("2d");
        ctx.drawImage(graphicImageImgSenID, 0, 0, canvas.width, canvas.height);

        /*
        console.log(tipoPregunta);
        console.log(imgWidth);
        console.log(imgHeight);
        console.log(imgWidth.value);
        console.log(imgHeight.value);
        console.log(coordinates);
        console.log(coordinates.value);
        */
        if ('' == coordinates.value) {
            console.log('Sin Coordenadas');
            PF('growlWV').renderMessage({
                    "summary": "Se nececita un",
                    "detail": "Poligono",
                    "severity": "error"
                }
            );
            return;
        }
        perimeter = JSON.parse(coordinates.value);
        console.log(perimeter);

        /*
        ctx.beginPath();
        ctx.moveTo(50,30);
        ctx.lineTo(75,55);
        ctx.lineTo(25,55);
        ctx.lineTo(50,30);
        ctx.fillStyle='blue';
        ctx.fill();
        */

        ctx.lineWidth = 1;
        ctx.strokeStyle = "white";
        ctx.lineCap = "square";
        ctx.beginPath();

        for (var i = 0; i < perimeter.length; i++) {
            if (i == 0) {
                ctx.moveTo(perimeter[i]['x'], perimeter[i]['y']);
            } else {
                ctx.lineTo(perimeter[i]['x'], perimeter[i]['y']);
            }
        }

        ctx.lineTo(perimeter[0]['x'], perimeter[0]['y']);
        ctx.closePath();
        ctx.fillStyle = 'rgba(255, 0, 0, 0.5)';
        ctx.fill();
        ctx.strokeStyle = 'blue';
        complete = true;
        ctx.stroke();

    }
    /** Variable declarada en otro archivo **/
    graphicImageImgCorID = document.getElementById('graphicImageImgCorID');
    if (null != graphicImageImgCorID) {
        console.log('Comienza null!=graphicImageImgCorID');

        $goJs = go.GraphObject.make;

        var img = new Image();
        img.src = graphicImageImgCorID.src;
        img.addEventListener('load', (e) => {

            diagram = $goJs(go.Diagram, "UpdateReactivosForm:imgCorDiv",
                {
                    fixedBounds: new go.Rect(0, 0, img.width, img.height),  // document is always 500x300 units
                    allowHorizontalScroll: false,  // disallow scrolling or panning
                    allowVerticalScroll: false,
                    allowZoom: false,              // disallow zooming
                    "animationManager.isEnabled": false,
                    "undoManager.isEnabled": true,
                    "ModelChanged": function (e) {     // just for demonstration purposes,
                        if (e.isTransactionFinished) {  // show the model data in the page's TextArea
                            document.getElementById("UpdateReactivosForm:coordinatesImgCor").textContent = e.model.toJson();
                        }
                    }
                });

            imgCorDivID = diagram.div;
            imgCorDivID.style.width = img.width + "px";
            imgCorDivID.style.height = img.height + "px";

            diagram.linkTemplate =
                $goJs(go.Link,
                    $goJs(go.Shape,
                        new go.Binding("stroke", "color"),  // shape.stroke = data.color
                        new go.Binding("strokeWidth", "thick")),  // shape.strokeWidth = data.thick
                    $goJs(go.Shape,
                        {toArrow: "OpenTriangle", fill: null},
                        new go.Binding("stroke", "color"),  // shape.stroke = data.color
                        new go.Binding("strokeWidth", "thick"))  // shape.strokeWidth = data.thick
                );

            diagram.nodeTemplate =
                $goJs(go.Node, "Auto"
                    , new go.Binding("location", "loc").makeTwoWay(),
                    $goJs(go.Shape,
                        new go.Binding("figure", "fig"),
                        new go.Binding("fill", "color"),
                        new go.Binding("stroke", "color"),
                        new go.Binding("strokeWidth", "thick")
                    ),
                    $goJs(go.TextBlock,
                        {margin: 5}, new go.Binding("text", "say"))
                );

            // the background Part showing the fixed bounds of the diagram contents
            diagram.add(
                $goJs(go.Part,
                    {layerName: "Grid", position: diagram.fixedBounds.position},
                    $goJs(go.Picture, e.target.src)
                ));

            diagram.model = go.Model.fromJson(coordinatesImgCorId.value);


        });

        coordinatesImgCorId = document.getElementById('UpdateReactivosForm:coordinatesImgCor');


        console.log('Finaliza null!=graphicImageImgCorID');
    }


    if (args.validationFailed) {
        argsValidationFailed();
    }
    console.log('Finaliza handleUpdateRequest');
}

/**** INICIA MOH ****/

function initImage() {

    let canvasContentName = 'canvas-content';

    let canvasContainer = document.getElementById('canvas-container');
    let canvasContent = document.getElementById(canvasContentName);

    canvasContainer.style.width = canvasWidth + 'px';
    canvasContainer.style.height = canvasHeight + 'px';

    canvasContent.style.width = canvasWidth + 'px';
    canvasContent.style.height = canvasHeight + 'px';

    this.initPolygon(canvasContentName);
}

function initPolygon(canvasContentName) {

    var $ = go.GraphObject.make;

    polygonDiagram = $(go.Diagram, canvasContentName);

    polygonDiagram.toolManager.mouseDownTools.insertAt(3, new GeometryReshapingTool());
    polygonDiagram.allowResize = true;
    polygonDiagram.allowReshape = true;
    polygonDiagram.allowRotate = true;

    this.updateAllAdornments();

    polygonDiagram.nodeTemplateMap.add("PolygonDrawing",
        $(go.Node,
            {locationSpot: go.Spot.Center},  // to support rotation about the center
            new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
            {
                selectionAdorned: true, selectionObjectName: "SHAPE",
                selectionAdornmentTemplate:  // custom selection adornment: a red rectangle
                    $(go.Adornment, "Auto",
                        $(go.Shape, {stroke: "red", fill: null}),
                        $(go.Placeholder, {margin: -1}))
            },
            {resizable: true, resizeObjectName: "SHAPE"},
            {rotatable: true, rotateObjectName: "SHAPE"},
            {reshapable: true},  // GeometryReshapingTool assumes nonexistent Part.reshapeObjectName would be "SHAPE"
            $(go.Shape,
                {name: "SHAPE", fill: "red", strokeWidth: 1},
                new go.Binding("desiredSize", "size", go.Size.parse).makeTwoWay(go.Size.stringify),
                new go.Binding("angle").makeTwoWay(),
                new go.Binding("geometryString", "geo").makeTwoWay(),
                new go.Binding("fill"),
                new go.Binding("stroke"),
                new go.Binding("strokeWidth"))
        ));
    // create polygon drawing tool for myDiagram, defined in PolygonDrawingTool.js
    var tool = new PolygonDrawingTool();

    // provide the default JavaScript object for a new polygon in the model
    tool.archetypePartData =
        {fill: "rgba(255,0,0,0.3)", stroke: "red", strokeWidth: 1.5, category: "PolygonDrawing"};
    tool.isPolygon = true;  // for a polyline drawing tool set this property to false

    // install as first mouse-down-tool
    polygonDiagram.toolManager.mouseDownTools.insertAt(0, tool);
    polygonDiagram.allowHorizontalScroll = false;
    polygonDiagram.allowVerticalScroll = false;

    this.loadPolygon();  // load a simple diagram from the textarea
}

function updateAllAdornments() {  // called after checkboxes change Diagram.allow...

    polygonDiagram.selection.each(function (p) {
        p.updateAdornments();
    });
}

function loadPolygon(reset = false) {

    let json = {};

    if (!reset) {

        const polygonData = document.getElementById('UpdateReactivosForm:coordinates').value;
        try{
        	 json = polygonData && polygonData !== '' ? JSON.parse(polygonData) : {};
        }catch(e){
        	
        }       
    }

    try {
        polygonDiagram.initialPosition = go.Point.parse(json.position ? json.position : "0 0");

        polygonDiagram.model = go.Model.fromJson(json.model ? json.model : {});

        polygonDiagram.model.undoManager.isEnabled = true;

        console.log(polygonDiagram);

    } catch (error) {

        console.log(error);
    }
}

function resetPolygon() {

    this.loadPolygon(true);
}

function savePolygon() {

    const polygonData = document.getElementById('UpdateReactivosForm:coordinates');

    if (polygonDiagram.model.nodeDataArray.length > 0) {
    	updatePosition()
        polygonData.value = '{ "position": "' + go.Point.stringify(polygonDiagram.position) + '",\n  "model": ' + polygonDiagram.model.toJson() + ' }';        
    }
}

function updatePosition(){
	var ar = polygonDiagram.model.nodeDataArray;
	for(var i=0;i<ar.length;i++){
		try{
			var poly = ar[i];
			if(poly.loc && poly.pointLoc !== poly.loc){				
				var loc = poly.loc.split(" ");
				var pointLoc = poly.pointLoc.split(" ");		
				var val = polyModel.getDiff(pointLoc[0], loc[0])
				polyModel.updatePoint(poly.pointX, val)
				val = polyModel.getDiff(pointLoc[1], loc[1])
				polyModel.updatePoint(poly.pointY, val)
				poly.pointLoc = poly.loc;
			}
			
			if(poly.size && poly.pointSize !== poly.size ){
				var size = poly.size.split(" ");
				var pointSize = poly.pointSize.split(" ");
				var diff =  polyModel.getDiff(pointSize[0], size[0])				
				polyModel.updateSize(poly.pointX, diff, polyModel.getSize(poly.pointX))	
				
				diff =  polyModel.getDiff(pointSize[1], size[1])
				polyModel.updateSize(poly.pointY, diff, polyModel.getSize(poly.pointY))
				
				poly.pointSize = poly.size;
			}
			
			if(poly.angle && poly.angle !== poly.pointAngle ){
				polyModel.rotatePolygon(poly, poly.angle)
				poly.pointAngle = poly.angle;
			}
			
		}catch(e){
			
		}
	}	
}

var polyModel = {
		getDiff : function(oldVal, newVal){
			var a = parseFloat(oldVal);
			var b = parseFloat(newVal);
			return b - a;
		},
		getPositiveDiff : function(oldVal, newVal){
			var a = parseFloat(oldVal);
			var b = parseFloat(newVal);
			var val = b-a;
			return val <0 ? -val : val;
		},
		getSize : function(ar){		
			return Math.max.apply(Math, ar) - Math.min.apply(Math, ar);
		},
		getCenter : function(ar){	
			 var min = Math.min.apply(Math, ar)
			 var size = polyModel.getSize(ar)
			return min + (size/2) 
		},
		updatePoint : function(array, val){
			for(var i=0;i<array.length;i++){
				array[i] = array[i] + val;
			}
		},
		updateSize : function(array, diff, size){			
			 var val = diff/2;
			 var center = polyModel.getCenter(array);			 
			 for(var i=0;i<array.length;i++){
				 var p = array[i];
					 if(p <= center){
						 p = p-val;
					 }else{
						 p = p+val;  
					 }
				 array[i] =p;
				}
		},
		rotatePolygon : function(poly, angle){
			 var cX = polyModel.getCenter(poly.pointX);
			 var cY = polyModel.getCenter(poly.pointY);
			 for(var i=0;i<poly.pointX.length;i++){			 
				 var p = polyModel.rotate(cX,cY,poly.pointX[i], poly.pointY[i], angle)
				 poly.pointX[i] = p[0];
				 poly.pointY[i] = p[1];
				 
			 }		 
		},
		rotate : function(cx, cy, x, y, angle) {
		    var radians = (Math.PI / 180) * angle,
	        cos = Math.cos(radians),
	        sin = Math.sin(radians),
	        nx = (cos * (x - cx)) + (sin * (y - cy)) + cx,
	        ny = (cos * (y - cy)) - (sin * (x - cx)) + cy;
		    return [nx, ny];
		}
}


function savePolygonComplete() {

    this.initImage();
}

/**** FIN MOH ****/



