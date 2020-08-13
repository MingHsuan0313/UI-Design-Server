package com.selab.uidesignserver.service;

import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.io.mxCodec;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.png.mxPngEncodeParam;
import com.mxgraph.util.png.mxPngImageEncoder;
import com.mxgraph.swing.handler.mxGraphHandler;


import org.w3c.dom.Document;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.*;
import com.selab.uidesignserver.config.Config;


public class CanvasToPictureUtil {
    public CanvasToPictureUtil(){}
    static public BufferedImage transformToPNG(String erXmlString) throws IOException{
        Document doc = mxXmlUtils.parseXml(erXmlString);
        System.out.println(doc);
        mxGraph graph = new mxGraph();
        mxCodec codec = new mxCodec(doc);
        codec.decode(doc.getDocumentElement(), graph.getModel());

        MymxGraphComponent graphComponent = new MymxGraphComponent(graph);
        BufferedImage image = mxCellRenderer.createBufferedImage(graph, null, 1, Color.WHITE, true, null, graphComponent.getCanvas());
        System.out.println(image);
        mxPngEncodeParam param = mxPngEncodeParam.getDefaultEncodeParam(image);
        param.setCompressedText(new String[] { "mxGraphModel", erXmlString });

        FileOutputStream outputStream = new FileOutputStream(new File("output/testPicture.png"));
        mxPngImageEncoder encoder = new mxPngImageEncoder(outputStream, param);

        if (image != null) {
            encoder.encode(image);
            return image;
        }
        outputStream.close();
        return null;
    }
}

class MymxGraphHandler extends mxGraphHandler {
    public MymxGraphHandler(mxGraphComponent graphComponent) {
        super(graphComponent);
    }

    protected void installDragGestureHandler() {
        //My Blank implementation for the installDragGestureHandler
    }
}

class MymxGraphComponent extends mxGraphComponent {
    public MymxGraphComponent(mxGraph graph) {
        super(graph);
    }

    protected mxGraphHandler createGraphHandler() {
        return new MymxGraphHandler(this);
    }
}


