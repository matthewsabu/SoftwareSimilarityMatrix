import os
from git.repo.base import Repo
from datetime import datetime


# create path name
path = "Codes" 

# create dataset dir
if not os.path.exists(path):
    os.makedirs(path)

# go to dir
os.chdir(path)

# acquire repositories
with open("D:\\Downloads\\College stuff\\SoftwareSimilarityMatrix\\Repo.txt",'r') as repo_list:
    for count, repo in enumerate(repo_list):    
       pair_id = "LBYCPD2_Pair" + str(count)
       print("Downloading ", pair_id)
       Repo.clone_from(repo.strip(), pair_id, branch='master')
   
print("---- COMPLETED ----")