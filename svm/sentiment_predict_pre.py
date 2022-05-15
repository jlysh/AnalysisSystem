# -- coding: utf-8 --
import warnings

from gensim.models.word2vec import Word2Vec
import numpy as np
import jieba
from sklearn.externals import joblib
import pandas as pd
import sys
import os
warnings.filterwarnings("ignore") #镇压警告


#去除停用词前代码

current_path = os.getcwd()
# 读取 Word2Vec 并对新输入进行词向量计算
def average_vec(words):
    # 读取 Word2Vec 模型
    w2v = Word2Vec.load(current_path+'\\..\\svm\\data\\w2v_model.pkl')
    vec = np.zeros(300).reshape((1, 300))
    for word in words:
        try:
            vec += w2v[word].reshape((1, 300))
        except KeyError:
            continue
    return vec

# 对实验楼评论进行情感判断
def svm_predict():
    csv_prefix = sys.argv[1] #传入前缀名(用户id)来判断打开哪个文件
    flag = sys.argv[2] #判断是keyword分析还是综合分析
    # 读取实验楼评论
    if flag == '0': # 0 是keyword分析
        print('keyword-------------------')
        df = pd.read_csv(current_path + "\\" + csv_prefix + "_comments_in_keyword.csv", error_bad_lines=False,header=0)
    if flag == '1': # 1 是综合分析
        df = pd.read_csv(current_path + "\\" + csv_prefix + "_comments_in.csv", error_bad_lines=False,header=0)
    comment_sentiment = []
    for string in df['article']:
        try:
            # 对评论分词
            words = jieba.lcut(str(string))
            words_vec = average_vec(words)
            # 读取支持向量机模型
            model = joblib.load(current_path + '\\..\\svm\\data\\svm_model.pkl')
            result = model.predict(words_vec)
            comment_sentiment.append(result[0])

            # 实时返回积极或消极结果
            if int(result[0]) == 1:
                print(string, '[积极]')
            else:
                print(string, '[消极]')
        except Exception as e:
            print(e)


    #将情绪结果合并到原数据文件中
    merged = pd.concat([df, pd.Series(comment_sentiment, name='emotion')], axis=1)

    # 储存文件
    if flag == '0':
        pd.DataFrame.to_csv(merged,current_path + "\\" + csv_prefix+'_comments_out_keyword.csv')
    if flag == '1':
        pd.DataFrame.to_csv(merged,current_path + "\\" + csv_prefix+'_comments_out.csv')
    print('done.')

# 执行
svm_predict()