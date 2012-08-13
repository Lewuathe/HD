#include <opencv2/opencv.hpp>
#include <string>
#include <vector>
#include <fstream>
#include <iostream>

/**
*
*
*
*/
class ImgDataset
{
public:
	void addImg(std::string filepath);
	std::string match(cv::Mat& img);
	void save();
	void save(std::string _filename);
	void load(std::string _filename);
private:
	std::string filename;//���[�h�����t�@�C����
	std::string filepathName;//�t�@�C���p�X���L�q���ꂽ�e�L�X�g�t�@�C��
	std::vector<cv::Mat> descriptors;//�摜�̓����x�N�g��
	std::vector<std::string> filepaths;//�e�摜�̃p�X
};