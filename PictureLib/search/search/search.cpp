#include <iostream>
#include <fstream>
#include <time.h>
#include <opencv2/opencv.hpp>
#include <vector>
#include "ImgDataset.h"

/**
*	���ݎ�������imgs/yyyymmddHHMMSS.jpg�`���̕������Ԃ�
*
*
*/
char * getFilename()
{
   time_t timer;
    struct tm *date;
    char str[256];

    timer = time(NULL);          /* �o�ߎ��Ԃ��擾 */
    date = localtime(&timer);    /* �o�ߎ��Ԃ����Ԃ�\���\���� date �ɕϊ� */
    strftime(str, 255, "imgs/%Y%m%d%H%M%S.jpg", date);
	return str;
}

/**
*	main�֐�
*
*	@param argc �R�}���h���C�������̐� 2�����҂����
*	@param argv �R�}���h���C�������@argv[1]�ɉ摜�̃p�X�����҂����
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
		if(key == 'e')//�I��
			break;
		else if(key == 'a')//�ǉ�����ꍇ
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