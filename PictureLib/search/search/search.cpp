#include <iostream>
#include <fstream>
#include <time.h>
#include <opencv2/opencv.hpp>
#include <vector>
#include "ImgDataset.h"

/**
*	現在時刻からimgs/yyyymmddHHMMSS.jpg形式の文字列を返す
*
*
*/
char * getFilename()
{
   time_t timer;
    struct tm *date;
    char str[256];

    timer = time(NULL);          /* 経過時間を取得 */
    date = localtime(&timer);    /* 経過時間を時間を表す構造体 date に変換 */
    strftime(str, 255, "imgs/%Y%m%d%H%M%S.jpg", date);
	return str;
}

/**
*	main関数
*
*	@param argc コマンドライン引数の数 2が期待される
*	@param argv コマンドライン引数　argv[1]に画像のパスが期待される
*/
int main(int argc,char** argv)
{

	ImgDataset ds;
	//ds.load("test.xml");
	ds.load("C:/www/hacknewday/search/search/test.xml");
	cv::Mat img = cv::imread(
		argv[1]);
		//"imgs/test.jpg");

	//std::string result=" test1 ";
	std::string result = ds.match(img);
	std::cout << result << std::endl;
	/*
	int key = cv::waitKey(1);
	cv::VideoCapture capture(0);

	capture >> img;
	while(1)
	{
		capture >> img;
		std::string result = ds.match(img);
		std::cout << result << std::endl;
		cv::imshow("img",img);
		int key = cv::waitKey(1);
		if(key == 'e')//終了
			break;
		else if(key == 'a')//追加する場合
		{
			std::string path = getFilename();
			cv::imwrite(path,img);
			ds.addImg(path);
		}
	}
	//ds.save();
	////*/

	return 0;
}