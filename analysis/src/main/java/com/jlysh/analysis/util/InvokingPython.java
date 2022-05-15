package com.jlysh.analysis.util;

import com.jlysh.analysis.AnalysisApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 江冷易水寒
 * @data 2021/4/30 10:07
 * @explain 调用python程序工具
 */

@Slf4j
@Configuration
public class InvokingPython {

    public void invokingPython(String csv_prefix,String flag) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("python",
                resolvePythonScriptPath("sentiment_predict.py"),csv_prefix,flag);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        List<String> results = readProcessOutput(process.getInputStream());
        log.info(results.toString());
    }
    private static String resolvePythonScriptPath(String filename) {
        File file = new File("../svm/" + filename);
        return file.getAbsolutePath();
    }

    private static List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }

}
