package com.maxgfr.traxivitytensorflow;

import android.content.res.AssetManager;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by maxime on 7/7/2017.
 */

public class TensorFlowClassifier {

    private TensorFlowInferenceInterface tfHelper;

    private String inputName;
    private String outputName;
    private boolean feedKeepProb;

    private float[] output;
    private String[] outputNames;

    private static TensorFlowClassifier INSTANCE = null;

    private TensorFlowClassifier(AssetManager assetManager, String modelPath,  String inputName, String outputName, boolean feedKeepProb) {
        this.inputName = inputName;
        this.outputName = outputName;
        this.tfHelper = new TensorFlowInferenceInterface(assetManager, modelPath);
        this.outputNames = new String[] { outputName };
        this.outputName = outputName;
        this.output = new float[6];
        this.feedKeepProb = feedKeepProb;
    }

    public static synchronized TensorFlowClassifier getInstance(AssetManager assetManager, String modelPath, String inputName, String outputName, boolean feedKeepProb) {
        if (INSTANCE == null)
        { 	INSTANCE = new TensorFlowClassifier(assetManager, modelPath,inputName, outputName, feedKeepProb);
        }
        return INSTANCE;
    }

    public List<String> recognize(List<Float> x, List<Float> y, List<Float> z) {

        List<Float> all = new ArrayList<Float>();
        List<String> out = new ArrayList<>();
        all.addAll(x);
        all.addAll(y);
        all.addAll(z);

        float[] input = toFloatArray(all);

        //using the interface : give it the input name, raw pixels from the drawing, input size
        tfHelper.feed(inputName,input,1,1,500,3);
        //probabilities
        if (feedKeepProb) {
            tfHelper.feed("keep_prob", new float[] { 1 });
        }
        //get the possible outputs
        tfHelper.run(outputNames);
        //get the output
        tfHelper.fetch(outputName, output);


        for (int i = 0; i < output.length; ++i) {
            out.add((String.valueOf(output[i])));
            System.out.println(output[i]);
        }

        return out;
    }

    private float[] toFloatArray(List<Float> list) {
        int i = 0;
        float[] array = new float[list.size()];

        for (Float f : list) {
            array[i++] = (f != null ? f : Float.NaN);
        }
        return array;
    }
}