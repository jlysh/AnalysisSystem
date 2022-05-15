# -- coding:utf-8 --

import pandas as pd
import jieba
import numpy as np

# 一、 获取分词之后的数组

#加载语料库，导入数据
neg = pd.read_excel('data/neg.xls',header = None,index = None)
pos = pd.read_excel('data/pos.xls',header = None,index = None)
# print(neg)
list = [1,2,3,4,5]
#jieba分词
word_cut = lambda x:jieba.lcut(x)
pos['words'] = pos[0].apply(word_cut)
neg['words'] = neg[0].apply(word_cut)

#使用1表示积极情绪，0表示消极情绪，并完成数组拼接
x = np.concatenate((pos['words'],neg['words']))
y = np.concatenate((np.ones(len(pos)),np.zeros(len(neg))))
print(x)

#将Ndarray保存为二进制文件备用
np.save('data/x_train.npy',x)
np.save('data/y_train.npy',y)
print('保存npy文件完成')

