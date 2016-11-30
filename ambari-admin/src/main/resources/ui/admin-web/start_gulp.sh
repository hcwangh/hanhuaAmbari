# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
 
#author: hcdream-zhangqj
#date: 20151203
#if [ $# -ne 1 ] ; then
#echo "usage: ['watch' or 'server' : start a webserver with port(default: 3000)]";
#exit 1;
#fi

#if [ ! $1 = "server" -a ! $1 = "watch" ] ; then
#echo "error: invaild arg!"
#echo "usage: ['watch' or 'server' : start a webserver with port(default: 3000)]";
#exit 1;
#fi

#if [ $1 = "server" ] ; then
#rm -rf gulpfile.js
#cp gulpfile.js.server gulpfile.js
#echo 'starting server..'
#fi

#if [ $1 = "watch" ] ; then
#rm -rf gulpfile.js
#cp gulpfile.js.watch gulpfile.js
#echo 'startubg watch..'
#fi
#nohup node node_modules/gulp/bin/gulp $1 > gulp_ambari_admin.log 2>&1 &
nohup node node_modules/gulp/bin/gulp watch > gulp_ambari_admin.log 2>&1 &
echo 'log in gulp_ambari_admin.log file..'
