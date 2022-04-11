/**
 * 
 *//**
 *
 */
let $goJs;
let contadorClicks = 0;
let puntoCreado = false;
let tipoReactivoID;
let paintingCanvasID;
let graphicImageImgSenID;
let graphicImageImgCorID;
let widthImgSenID;
let heightImgSenID;
let ctx;
let perimeter = new Array();
let coordinatesID;
let coordinatesImgCorId;
let diagram;
let imgCorDivID;
let widthImgCorID;
let heightImgCorID;
let val=0;
const INF = 10000;

class Point {
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    console.log('Comienza DOMContentLoaded');
    tipoReactivoID = document.getElementById('mqrsExaForm:tipoReactivo');
    paintingCanvasID = document.getElementById('paintingCanvas');
    graphicImageImgSenID = document.getElementById('mqrsExaForm:graphicImageImgSen');
    graphicImageImgCorID = document.getElementById('mqrsExaForm:graphicImageImgCor');
    widthImgSenID = document.getElementById('mqrsExaForm:widthImgSen');
    heightImgSenID = document.getElementById('mqrsExaForm:heightImgSen');

    console.log(tipoReactivoID.value);
    if (tipoReactivoID.value !== 'IMAGEN_INDICADA' && tipoReactivoID.value !== 'IMAGEN_ANOTADA') {
        return;
    }

    if ('IMAGEN_INDICADA' === tipoReactivoID.value) {

        var img = new Image();
        img.setAttribute('src', graphicImageImgSenID.src);
        img.addEventListener('load', (e) => {
            paintingCanvasID.width = img.width;
            paintingCanvasID.height = img.height;
            ctx = paintingCanvasID.getContext("2d");
            ctx.drawImage(img, 0, 0, paintingCanvasID.width, paintingCanvasID.height);

        });

        paintingCanvasID.addEventListener("mousemove", function (e) {
            var cRect = paintingCanvasID.getBoundingClientRect(); // Gets CSS pos, and width/height
            var canvasX = Math.round(e.clientX - cRect.left); // Subtract the 'left' of the canvas
            var canvasY = Math.round(e.clientY - cRect.top); // from the X/Y positions to make
            document.getElementById('mqrsExaForm:coordinatesMouseMove').value = "X: " + canvasX + ", Y: " + canvasY;
        });
    }

    if ('IMAGEN_ANOTADA' === tipoReactivoID.value) {
        console.log("*");
        widthImgCorID = document.getElementById('mqrsExaForm:widthImgCor');
        heightImgCorID = document.getElementById('mqrsExaForm:heightImgCor');
        $goJs = go.GraphObject.make;
        var img = new Image();
        img.setAttribute('src', graphicImageImgCorID.src);
        img.addEventListener('load', (e) => {


            diagram = $goJs(go.Diagram, "mqrsExaForm:imgCorDiv",
                {
                    fixedBounds: new go.Rect(0, 0, img.width, img.height),  // document is always 500x300 units
                    allowHorizontalScroll: false,  // disallow scrolling or panning
                    allowVerticalScroll: false,
                    allowZoom: false,              // disallow zooming
                    "animationManager.isEnabled": false,
                    "undoManager.isEnabled": true,
                    isReadOnly: true
                });

            imgCorDivID = diagram.div;
            imgCorDivID.style.width = img.width + "px";
            imgCorDivID.style.height = img.height + "px";

            /**
             diagram.grid.visible = true;
             diagram.startTransaction("change Layout");
             var lay = diagram.layout;
             lay.alignment = go.GridLayout.Location;
             lay.wrappingWidth = img.width;
             diagram.commitTransaction("change Layout");
             ***/

            /*** Bug Alteracion de Posicionamiento **/
            coordinatesImgCorId = document.getElementById('mqrsExaForm:coordinatesImgCor');
            console.log(coordinatesImgCorId.value);
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
                    , {dragComputation: stayInFixedArea}
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
    }


    console.log('Finaliza DOMContentLoaded');
});


function point_it(event) {
	
    val=val+1;
    var sc=parseInt(document.getElementById("mqrsExaForm:numeroPoligonos").value);  
    if(val <= 0){    
    paintingCanvasID = document.getElementById("paintingCanvas");
    ctx = paintingCanvasID.getContext("2d");
    ctx.clearRect(0, 0, paintingCanvasID.width, paintingCanvasID.height);
    }
    var img = new Image();
    img.setAttribute('src', graphicImageImgSenID.src);
    
    
    img.addEventListener('load', (e) => {
    	if(val <= 0){
        paintingCanvasID.width = img.width;
       paintingCanvasID.height = img.height;
       ctx = paintingCanvasID.getContext("2d");
       ctx.drawImage(img, 0, 0, paintingCanvasID.width, paintingCanvasID.height);
       var rect, x, y;
       if (event.ctrlKey || event.which === 3 || event.button === 2) {
           event.preventDefault();
            return;
       }M2
   	} 
       if(val<=sc){
        rect = paintingCanvasID.getBoundingClientRect();
        x = event.clientX - rect.left;
        y = event.clientY - rect.top;
       x = x.toFixed(5);
       y = y.toFixed(5);
       perimeter.push({'x': x, 'y': y});

        setPointAnswer(x,y).then((polygon) => {
          
          if(polygon.length > 3 && isInside(polygon, polygon.length, new Point(x, y))){
        	  model.updateScore();
          }
          
          	  var indicateImageResult = document.getElementById('mqrsExaForm:indicateImageResult');
          	  var  puntuacionScore = document.getElementById('mqrsExaForm:puntuacionScore');
          	   indicateImageResult.value = JSON.parse(model.getResult());
          	   puntuacionScore.value = model.getScore();		   
          	  
          
         
           //indicateImageResult = document.getElementById('mqrsExaForm:indicateImageResult');

          //console.log(indicateImageResult);

          
       });
        
        var tmpX, tmpY;

        ctx.strokeStyle = 'red';
        ctx.beginPath();
        tmpX = parseFloat(x) + 20;
       ctx.moveTo(tmpX, y);
        ctx.lineTo(x, y);
       tmpX = parseFloat(x) - 20;
       ctx.moveTo(tmpX, y);
        ctx.lineTo(x, y);
        tmpY = parseFloat(y) + 20;
        ctx.moveTo(x, tmpY);
       ctx.lineTo(x, y);
       tmpY = parseFloat(y) - 20;
       ctx.moveTo(x, tmpY);
        ctx.lineTo(x, y);
       ctx.lineWidth = 3;
        ctx.stroke();
	
       coordinatesID = document.getElementById('mqrsExaForm:coordinates');
       coordinatesID.value = JSON.stringify(perimeter);
    }});

}


function init() {
	debugger;
	
    var divArea = document.getElementById('myDiagramDiv');
    divArea.onclick = function () {
        addShape();
    };
   
}

function addShape() {
    contadorClicks = contadorClicks + 1;
    var numClicks = contadorClicks;

    if (!puntoCreado) {
        puntoCreado = true
        var $ = go.GraphObject.make;
        myDiagram = $(go.Diagram, "myDiagramDiv");
        myDiagram.add(
            $(go.Part, "auto",
                $(go.Shape, "PlusLine", {width: 40, height: 40, margin: 4, fill: "red", stroke: "red", strokeWidth: 2})
            )
        );
        console.log(myDiagram);
        
    } else {
        //alert('logica faltante');
    }
}



var model = {
		polygons : [],
		correctPoly : [],
		polyValue : 0,
		score : 0,
		resultScore : 0,
		updateScore : function()	{
			model.resultScore += model.polyValue;
		},
		remove : function(index){
			if(index >  -1 &&  index < model.polygons.length)
				model.polygons.splice(index, 1);
		}, getResult : function(){
			let r = model.resultScore / model.score;
			return r >= 0.5;
		},getScore : function(){
			let r = model.resultScore / model.score;
			if(r >= 0.5){
				r  = r >  0.5 ? 1 : 0.5
			}else{
				r = 0;
			}
			return r;
		}
	}

$(document).ready(function () {

    console.log("Comienza ready");

    console.log("Finaliza ready");
    try{
    	        
        let polygonPoints = JSON.parse(document.getElementById('mqrsExaForm:coordinatesImgCor').value);
        model.score = parseInt(document.getElementById('mqrsExaForm:score').value);
    	
    	if(polygonPoints.model && polygonPoints.model.nodeDataArray && polygonPoints.model.nodeDataArray.length > 0){
    		model.polygons = polygonPoints.model.nodeDataArray;
    		model.polyValue = model.score / polygonPoints.model.nodeDataArray.length;
    	}
    	
    	
        }catch(e){
        	
        }

});

function handleUpdateRequest(xhr, status, args) {
   
    if (args.validationFailed || !args.updateIn) {
        argsValidationFailed();
    }
}

// this function is the Node.dragComputation, to limit the movement of the parts
// use GRIDPT instead of PT if DraggingTool.isGridSnapEnabled and movement should snap to grid
function stayInFixedArea(part, pt, gridpt) {
    var diagram = part.diagram;
    if (diagram === null) return pt;
    // compute the document area without padding
    var v = diagram.documentBounds.copy();
    v.subtractMargin(diagram.padding);
    // get the bounds of the part being dragged
    var b = part.actualBounds;
    var loc = part.location;
    // now limit the location appropriately
    var x = Math.max(v.x, Math.min(pt.x, v.right - b.width)) + (loc.x - b.x);
    var y = Math.max(v.y, Math.min(pt.y, v.bottom - b.height)) + (loc.y - b.y);
    return new go.Point(x, y);
}

function checkcheck(x, y, cornersX, cornersY) {

    var i, j = cornersX.length - 1;
    var odd = false;

    var pX = cornersX;
    var pY = cornersY;

    for (i = 0; i < cornersX.length; i++) {
        if ((pY[i] < y && pY[j] >= y || pY[j] < y && pY[i] >= y)
            && (pX[i] <= x || pX[j] <= x)) {
            odd ^= (pX[i] + (y - pY[i]) * (pX[j] - pX[i]) / (pY[j] - pY[i])) < x;
        }
        
        j = i;
    }

    return odd;
}

function isPointInside(x, y, cornersX, cornersY) {

    let corners = cornersX.length - 2;

    let inside = false;

    for (let i = 0; i < (cornersX.length - 1); i++) {

        if ((cornersY[i] < y && cornersY[corners] >= y
            || cornersY[corners] < y && cornersY[i] >= y)
            && (cornersX[i] <= x || cornersX[corners] <= x)) {

            if (((cornersX[i] + (y - cornersY[i])) / ((cornersY[corners] - cornersY[i]) * (cornersX[corners] - cornersX[i]))) < x) {

                inside = !inside;
            }
        }

        corners = i;
    }

    return inside;
}

function inside(point, vs) {
    // ray-casting algorithm based on
    // https://wrf.ecse.rpi.edu/Research/Short_Notes/pnpoly.html/pnpoly.html
    
    var x = point[0], y = point[1];
    
    var inside = false;
    for (var i = 0, j = vs.length - 1; i < vs.length; j = i++) {
        var xi = vs[i][0], yi = vs[i][1];
        var xj = vs[j][0], yj = vs[j][1];
        
        var intersect = ((yi > y) != (yj > y))
            && (x < (xj - xi) * (y - yi) / (yj - yi) + xi);
        if (intersect) inside = !inside;
    }
    
    return inside;
};

function onSegment(p, q, r) {

    if (q.x <= Math.max(p.x, r.x) &&
        q.x >= Math.min(p.x, r.x) &&
        q.y <= Math.max(p.y, r.y) &&
        q.y >= Math.min(p.y, r.y)) {
        return true;
    }

    return false;
}

function getPolygonArray(polygon){
	var poly = [];
	var pointX = polygon.pointX;
	
}

function orientation(p, q, r) {

    let val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

    if (val == 0) {

        return 0; // colinear
    } type="hidden"

    return (val > 0) ? 1 : 2; // clock or counterclock wise
}

function doIntersect(p1, q1, p2, q2) {
    // Find the four orientations needed for
    // general and special cases
    let o1 = orientation(p1, q1, p2);
    let o2 = orientation(p1, q1, q2);
    let o3 = orientation(p2, q2, p1);
    let o4 = orientation(p2, q2, q1);

    // General case
    if (o1 != o2 && o3 != o4) {

        return true;
    }

    // Special Cases
    // p1, q1 and p2 are colinear and
    // p2 lies on segment p1q1
    if (o1 == 0 && onSegment(p1, p2, q1)) {

        return true;
    }

    // p1, q1 and p2 are colinear and
    // q2 lies on segment p1q1
    if (o2 == 0 && onSegment(p1, q2, q1)) {

        return true;
    }

    // p2, q2 and p1 are colinear and
    // p1 lies on segment p2q2
    if (o3 == 0 && onSegment(p2, p1, q2)) {

        return true;
    }

    // p2, q2 and q1 are colinear and
    // q1 lies on segment p2q2
    if (o4 == 0 && onSegment(p2, q1, q2)) {

        return true;
    }

    // Doesn't fall in any of the above cases
    return false;
}

function isInside(polygon, n, p) {

    // There must be at least 3 vertices in polygon[]
    if (n < 3) {

        return false;
    }

    // Create a point for line segment from p to infinite
    const extreme = new Point(INF, p.y);

    // Count intersections of the above line
    // with sides of polygon
    let count = 0, i = 0;

    do {
        let next = (i + 1) % n;

        // Check if the line segment from 'p' to
        // 'extreme' intersects with the line
        // segment from 'polygon[i]' to 'polygon[next]'
        if (doIntersect(polygon[i], polygon[next], p, extreme)) {
            // If the point 'p' is colinear with line
            // segment 'i-next', then check if it lies
            // on segment. If it lies, return true, otherwise false
            if (orientation(polygon[i], p, polygon[next]) == 0) {

                return onSegment(polygon[i], p, polygon[next]);
            }

            count++;
        }

        i = next;

    } while (i != 0);

    // Return true if count is odd, false otherwise
    return (count % 2 == 1); // Same as (count%2 == 1)
}

/**
 * 
 * @param posX
 * @param polyX
 * @returns
 *
function getShifting(posX, polyX){
	if(posX < 0 && polyX < 0){
		return ((posX * -1) - (polyX * -1) ) * -1;
	}
	
	if(posX > 0 && polyX > 0){
		return (posX - polyX ) * -1;
	}else{
		
	}
	
}
/**
 * 
 * @param x
 * @param y
 * @returns
 */



function setPointAnswer(x,y) {

    return new Promise(resolve => {

        let polygon = [];
        let cornersX=[];
        let cornersY=[];
        var count = 1;
        var finalResult = 0;
        let index = -1;
        let removeIndex = -1;
     
        var resultPolygon = [];
        Array.prototype.forEach.call(model.polygons,node => {
        		var nod=node.geo.split(" ");
        		console.log(nod);
        		let xPOS
        		index++;
        		cornersX=node.pointX;
    			cornersY=node.pointY;
        		console.log("cornersX : "+cornersX)
        		console.log("cornersY : "+cornersY)
        		var result = checkcheck(x, y, cornersX, cornersY)
        		if(result > 0 || result != false) {
          			for (var i = 0; i < cornersX.length; i++) {
        				resultPolygon.push({x: cornersX[i], y: cornersY[i]})
        		    }     	
          			removeIndex = index;
        		}     		
           });
           if(resultPolygon.length > 3){
        	   model.correctPoly.push(model.polygons[removeIndex])
        	   model.remove(removeIndex);
         	   
           }
           resolve(resultPolygon)
        
//        indicateImageResult = document.getElementById('mqrsExaForm:indicateImageResult');
//        console.log("indicateImageResult: "+indicateImageResult);
//
//        indicateImageResult.value = JSON.stringify(finalResult);
//        console.log("indicateImageResult.value: "+indicateImageResult.value);
//        
//        console.log("finalResult : "+finalResult);
    });
}

var  rectivosExam = {
		
		imageAnotoda : function(){			
			tipoReactivoID = document.getElementById('mqrsExaForm:tipoReactivo');
		    paintingCanvasID = document.getElementById('paintingCanvas');
		    graphicImageImgSenID = document.getElementById('mqrsExaForm:graphicImageImgSen');
		    graphicImageImgCorID = document.getElementById('mqrsExaForm:graphicImageImgCor');
		    widthImgSenID = document.getElementById('mqrsExaForm:widthImgSen');
		    heightImgSenID = document.getElementById('mqrsExaForm:heightImgSen');
		    
				widthImgCorID = document.getElementById('mqrsExaForm:widthImgCor');
		        heightImgCorID = document.getElementById('mqrsExaForm:heightImgCor');
		        $goJs = go.GraphObject.make;
		        var img = new Image();
		        img.setAttribute('src', graphicImageImgCorID.src);
		        img.addEventListener('load', (e) => {


		            diagram = $goJs(go.Diagram, "mqrsExaForm:imgCorDiv",
		                {
		                    fixedBounds: new go.Rect(0, 0, img.width, img.height),  // document is always 500x300 units
		                    allowHorizontalScroll: false,  // disallow scrolling or panning
		                    allowVerticalScroll: false,
		                    allowZoom: false,              // disallow zooming
		                    "animationManager.isEnabled": false,
		                    "undoManager.isEnabled": true,
		                    isReadOnly: true
		                });

		            imgCorDivID = diagram.div;
		            imgCorDivID.style.width = img.width + "px";
		            imgCorDivID.style.height = img.height + "px";

		            /**
		             diagram.grid.visible = true;
		             diagram.startTransaction("change Layout");
		             var lay = diagram.layout;
		             lay.alignment = go.GridLayout.Location;
		             lay.wrappingWidth = img.width;
		             diagram.commitTransaction("change Layout");
		             ***/

		            /*** Bug Alteracion de Posicionamiento **/
		            coordinatesImgCorId = document.getElementById('mqrsExaForm:coordinatesImgCor');
		            console.log(coordinatesImgCorId.value);
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
		                    , {dragComputation: stayInFixedArea}
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
		}
		
		
}
