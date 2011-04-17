'''
Created on 21-03-2011

@author: maciek
'''
from IndexGenerator import IndexGenerator
from optparse import OptionParser
import os
import tempfile
import shutil
import logging

logging.basicConfig(level = logging.DEBUG)

parser = OptionParser()
parser.add_option('-n', '--app-name', action='store', dest='appName', help='aplication name')
parser.add_option('-u', '--release-url', action='store', dest='releaseUrl', help='URL of directory where OTA files are externally visible')
parser.add_option('-d', '--destination-directory', action='store', dest='otaAppDir', help='Directory where OTA files are created')
parser.add_option('-v', '--version', action='store', dest='version', help='Version of the application')
parser.add_option('-r', '--release', action='store', dest='release', help='Release of the application')
parser.add_option('-R', '--release-notes', action='store', dest='releaseNotes', help='Release notes of the application (in txt2tags format)')
parser.add_option('-D', '--description', action='store', dest='description', help='Description of the application (in txt2tags format)')

(options, args) = parser.parse_args()

if options.appName == None:
    parser.error("Please specify the appName.")
elif options.releaseUrl == None:
    parser.error("Please specify releaseUrl")
elif options.otaAppDir == None:
    parser.error("Please specify destination directory")
elif options.version == None:
    parser.error("Please specify version")
elif options.release == None:
    parser.error("Please specify release")
elif options.releaseNotes == None:
    parser.error("Please specify releaseNotes")
elif options.description == None:
    parser.error("Please specify description")

appName = options.appName
releaseUrl = options.releaseUrl
otaAppDir = options.otaAppDir
version = options.version
release = options.release
releaseNotes = options.releaseNotes
description = options.description

def findIconFilename():
    iconPath = "res/drawable-hdpi/icon.png"
    if not os.path.exists(iconPath):
        iconPath = "res/drawable-mdpi/icon.png"
    if not os.path.exists(iconPath):
        iconPath = "res/drawable-ldpi/icon.png"
    if not os.path.exists(iconPath):
        iconPath = "res/drawable/icon.png"

    logging.debug("IconPath: "+iconPath)
    return iconPath

def createOTApackage():
    '''
    crates all needed files in tmp dir
    '''

    releaseNotesContent = open(releaseNotes).read()
    descriptionContent = open(description).read()
    indexGenerator = IndexGenerator(appName, releaseUrl, releaseNotesContent, descriptionContent, version, release)
    index = indexGenerator.get();
    tempIndexFile = tempfile.TemporaryFile()
    tempIndexFile.write(index)
    tempIndexFile.flush()
    tempIndexFile.seek(0)

    return tempIndexFile


tempIndexFile = createOTApackage()
if not os.path.isdir(otaAppDir):
    logging.debug("creating dir: "+otaAppDir)
    os.mkdir(otaAppDir)
else:
    logging.warning("dir: "+otaAppDir+" exists")
indexFile = open(os.path.join(otaAppDir,"index.html"),'w')
shutil.copyfileobj(tempIndexFile, indexFile)
srcIconFileName = findIconFilename()
disIconFileName = os.path.join(otaAppDir,"Icon.png")
shutil.copy(srcIconFileName,disIconFileName)
