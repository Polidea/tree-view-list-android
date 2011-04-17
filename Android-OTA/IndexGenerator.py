'''
Created on 21-03-2011

@author: maciek
'''
from formater import formatString
import os




class IndexGenerator(object):
    '''
    Generates Index.html for iOS app OTA distribution
    '''
    basePath = os.path.dirname(__file__)
    templateFile = os.path.join(basePath,"templates/index.tmpl")
    releaseUrl = ""
    appName = ""
    changeLog = ""
    description = ""
    version = ""
    release = ""

    def __init__(self,appName, releaseUrl, changeLog, description, version, release):
        '''
        Constructor
        '''
        self.appName = appName
        self.releaseUrl = releaseUrl
        self.changeLog = changeLog
        self.description = description
        self.version = version
        self.release = release

    def get(self):
        '''
        returns index.html source code from template file
        '''

        template = open(self.templateFile).read()
        index = formatString(template, releaseUrl=self.releaseUrl,
                                changeLog=self.changeLog,
                                appName=self.appName,
                                description=self.description,
                                version = self.version,
                                release = self.release);

        return index

