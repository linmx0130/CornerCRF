CornerCRF
====
Yet another linear chain CRF implementation for Chinese word segmentation in Java.

#Introduction
##Training Data
输入数据格式为：每行一个字，空格，然后是分词标记（BEIS），句子末尾使用一个空行。可以看train.utf8的内容为demo。只能使用UTF-8编码。
##Usage
核心类为LinearCRF类，应当首先用addTag方法添加Tags，然后用addFunction方法添加特征函数。特征函数对象应当实现IFeatureFunction。
获得数据后，需要用feedDataToBuildFunction将数据传入CRF对象，初始化特征函数。然后迭代多次training函数，训练参数。
最后，用predict方法传入要预测的句子，可以得到预测结果的标记字符串。

#About
##License
This project is released under MIT License. Read LICENSE for more details.
##References
* *Chinese Segmentation and New Word Detection using Conditional Random Fields*, Fuchun Peng, Fangfang Feng, Andrew McCallum. CoLING 2004
* *Discriminative Training Methods for Hidden Markov Models: Theory and Experiments with Perceptron Algorithms*, Michael Collins. EMNLP 2002