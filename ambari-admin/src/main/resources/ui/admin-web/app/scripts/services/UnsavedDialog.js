/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
'use strict';

angular.module('ambariAdminConsole')
.service('UnsavedDialog', ['$modal', function($modal) {

	return function(){
		var modalInstance = $modal.open({
      template: '<div class="modal-header"><h3 class="modal-title">警告</h3></div><div class="modal-body">变动未保存. 保存改变还是丢弃?</div><div class="modal-footer"><div class="btn btn-default" ng-click="cancel()">取消</div><div class="btn btn-warning" ng-click="discard()">放弃</div><div class="btn btn-primary" ng-click="save()">保存</div></div>',
      controller: ['$scope', '$modalInstance', function($scope, $modalInstance) {
        $scope.save = function() {
          $modalInstance.close('save');
        };
        $scope.discard = function() {
          $modalInstance.close('discard');
        };
        $scope.cancel = function() {
          $modalInstance.close('cancel');
        };
      }]
    });
    
    return modalInstance.result;
	};
}]);
