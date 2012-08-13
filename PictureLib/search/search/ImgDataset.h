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
	std::string filename;//ロードしたファイル名
	std::string filepathName;//ファイルパスが記述されたテキストファイル
	std::vector<cv::Mat> descriptors;//画像の特徴ベクトル
	std::vector<std::string> filepaths;//各画像のパス
};