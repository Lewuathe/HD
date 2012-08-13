#include "ImgDataset.h"
const double THRESHOLD_DISTANCE = .2;
const double THRESHOLD_SIMILARITY = 0.15;

/**
*	�������o���s���f�[�^��ǉ�
*
*	@param filename�@�ǉ�����摜�̃p�X
*/
void ImgDataset::addImg(std::string filename)
{
	cv::Mat img = cv::imread(filename,0);//�O���C�X�P�[���œǂݍ���
	cv::Mat descriptor;
	std::vector<cv::KeyPoint> keypoints;
	cv::SurfFeatureDetector detector;
	cv::SurfDescriptorExtractor extractor;
	detector.detect(img,keypoints);
	extractor.compute(img,keypoints,descriptor);
	filepaths.push_back(filename);
	descriptors.push_back(descriptor.clone());
	return;
}

/**
*	�f�[�^�Z�b�g�̕ۑ�
*
*/
void ImgDataset::save()
{
	save(filename);
}

/**
*	�f�[�^�Z�b�g�̕ۑ�
*
*	@param _filename �ۑ���xml�t�@�C����
*
*/
void ImgDataset::save(std::string _filename)
{
	cv::FileStorage fs(_filename,cv::FileStorage::WRITE);
	fs << "filepaths" << filepathName ;
	//store descriptors
	fs << "descriptors" << "[" ;
	for(size_t i = 0; i < descriptors.size() ; i++)
		fs << descriptors[i];
	fs << "]" ;

	//store filepaths to files.txt
	std::ofstream file(filepathName);

	for(size_t i = 0; i < filepaths.size() ; i++)
		file << filepaths[i] << std::endl;

	return;
}

/**
*
*
*
*
*
*/
void ImgDataset::load(std::string _filename)
{
	filename = _filename;
	cv::FileStorage fs(_filename,cv::FileStorage::READ);
	filepathName = static_cast<std::string>(fs["filepaths"]);
	cv::FileNode node = fs["descriptors"];
	cv::Mat descriptor;
	for(cv::FileNodeIterator it = node.begin() ; it != node.end() ; it++)
	{
		*it >> descriptor;
		descriptors.push_back(descriptor.clone());
	}
	std::ifstream file(filepathName);
	std::string path;
	while(!file.eof())
	{
		file >> path;
		if(path.empty())
			break;
		filepaths.push_back(path);
	}
	return;
}

/**
*
*
*
*
*/
std::string ImgDataset::match(cv::Mat& img)
{
	cv::Mat gray;
	std::string resultString;
	resultString = "<pictures>\n";
	if(img.channels() == 3)
		cv::cvtColor(img,gray,CV_BGR2GRAY);
	else
		gray = img.clone();

	cv::Mat descriptor;
	std::vector<cv::KeyPoint> keypoints;
	cv::SurfFeatureDetector detector;
	cv::SurfDescriptorExtractor extractor;
	detector.detect(gray,keypoints);
	extractor.compute(gray,keypoints,descriptor);
	cv::FlannBasedMatcher matcher;
	std::vector< cv::DMatch > matches;
	std::vector<double> results(descriptors.size(),0);// �ŋߖT�̃f�B�X�N���v�^�Ƃ̋�����臒l�ȉ��̃f�B�X�N���v�^��/�e���v���[�g���̃f�B�X�N���v�^�̐�
	for(size_t i = 0; i < descriptors.size() ; i++)
	{
		matches.clear();
		matcher.match(descriptors[i],descriptor,matches);
		for(size_t j = 0; j < matches.size() ; j++)
		{
			if(matches[j].distance < THRESHOLD_DISTANCE)
				results[i]++;
		}
		if(descriptors[i].rows < 1)
			continue;
		results[i] /= descriptors[i].rows;
		if(results[i] > THRESHOLD_SIMILARITY)
		{
			resultString += "	<picture uri=\"";
			resultString += filepaths[i];
			resultString += "\" />\n";
		}
	}

	resultString += "</pictures>";
	return resultString;
}