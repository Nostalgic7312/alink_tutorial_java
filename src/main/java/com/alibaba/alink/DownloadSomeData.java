package com.alibaba.alink;

import com.alibaba.flink.ml.tf2.shaded.org.apache.commons.io.FileUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

public class DownloadSomeData {
    public static void main(String[] args) throws Exception {

        downloadUrl(
                "http://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data",
                Chap03.LOCAL_DIR
        );
        downloadUrl(
                "http://archive.ics.uci.edu/ml/machine-learning-databases/wine-quality/winequality-white.csv",
                Chap03.LOCAL_DIR
        );
        downloadUrl(
                "http://files.grouplens.org/datasets/movielens/ml-100k/u.data",
                Chap03.LOCAL_DIR
        );
        downloadUrl(
                "http://archive.ics.uci.edu/ml/machine-learning-databases/00267/data_banknote_authentication.txt",
                Chap08.DATA_DIR
        );
        downloadUrl(
                "http://archive.ics.uci.edu/ml/machine-learning-databases/mushroom/agaricus-lepiota.data",
                Chap09.DATA_DIR
        );
        downloadUrl(
                "http://archive.ics.uci.edu/ml/machine-learning-databases/statlog/german/german.data",
                Chap10.DATA_DIR
        );
        downloadUrl(
                "http://alink-release.oss-cn-beijing.aliyuncs.com/data-files/action_log.csv",
                Chap11.DATA_DIR
        );
        downloadUrl(
                "http://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data",
                Chap12.DATA_DIR
        );
        downloadUrl(
                "http://yann.lecun.com/exdb/mnist/train-images-idx3-ubyte.gz",
                Chap13.DATA_DIR
        );
        downloadUrl(
                "http://yann.lecun.com/exdb/mnist/train-labels-idx1-ubyte.gz",
                Chap13.DATA_DIR
        );
        downloadUrl(
                "http://yann.lecun.com/exdb/mnist/t10k-images-idx3-ubyte.gz",
                Chap13.DATA_DIR
        );
        downloadUrl(
                "http://yann.lecun.com/exdb/mnist/t10k-labels-idx1-ubyte.gz",
                Chap13.DATA_DIR
        );
        downloadUrl(
                "http://archive.ics.uci.edu/ml/machine-learning-databases/wine-quality/winequality-white.csv",
                Chap16.DATA_DIR
        );

        for (String fileName : new String[]{Chap24.ITEM_FILE, Chap24.USER_FILE,
                Chap24.RATING_FILE, Chap24.RATING_TRAIN_FILE, Chap24.RATING_TEST_FILE
        }) {
            downloadUrl(
                    "http://files.grouplens.org/datasets/movielens/ml-100k/" + fileName,
                    Chap24.DATA_DIR
            );
        }

        downloadUrlHttps(
                "https://raw.githubusercontent.com/tennessine/corpus/master/%E4%B8%89%E5%9B%BD%E6%BC%94%E4%B9%89.txt",
                Chap22.DATA_DIR
        );
        downloadUrlHttps(
                "https://github.com/BenDerPan/toutiao-text-classfication-dataset/raw/master/toutiao_cat_data.txt.zip",
                Chap21.DATA_DIR
        );

    }

    static synchronized void downloadUrl(String url, String dirPath) {
        try {
            URL httpUrl = new URL(url);
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            FileUtils.copyURLToFile(httpUrl, new File(dir, fileName));
//            FileUtils.copyURLToFile(httpUrl, new File(dir, fileName), 30000, 10000);
            System.out.println("Success @ " + url);
        } catch (Exception e) {
            System.err.println("Failed @ " + url);
            System.err.println(e.toString());
        }
    }

    static synchronized void downloadUrlHttps(String url, String dirPath) {
        try {
            URL httpsUrl = new URL(url);
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String str = URLDecoder.decode(url, "UTF-8");
            String fileName = str.substring(str.lastIndexOf("/") + 1);

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
            HttpsURLConnection connection = (HttpsURLConnection) httpsUrl.openConnection();

            FileUtils.copyURLToFile(connection.getURL(), new File(dir, fileName), 60000, 50000);
            System.out.println("Success @ " + url);
        } catch (Exception e) {
            System.err.println("Failed @ " + url);
            System.err.println(e.toString());
        }
    }

}
