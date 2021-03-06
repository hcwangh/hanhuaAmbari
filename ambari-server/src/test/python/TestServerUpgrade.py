'''
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
'''

import StringIO
import sys
from ambari_commons.exceptions import FatalException
from unittest import TestCase
from mock.mock import patch, MagicMock
from ambari_server.serverUpgrade import set_current, SetCurrentVersionOptions, upgrade_stack
import ambari_server


class TestServerUpgrade(TestCase):

  @patch("ambari_server.serverUpgrade.is_server_runing")
  @patch('ambari_server.serverUpgrade.SetCurrentVersionOptions.no_finalize_options_set')
  @patch('ambari_server.serverUpgrade.get_validated_string_input')
  @patch('ambari_server.serverUpgrade.get_ambari_properties')
  @patch('ambari_server.serverUtils.get_ambari_server_api_base')
  @patch('ambari_commons.logging_utils.get_verbose')
  @patch('urllib2.urlopen')
  def test_set_current(self, urlopen_mock, get_verbose_mock, get_ambari_server_api_base_mock,
                       get_ambari_properties_mock, get_validated_string_input_mock,
                       no_finalize_options_set_mock, is_server_runing_mock):
    options = MagicMock()
    options.cluster_name = 'cc'
    options.desired_repo_version = 'HDP-2.2.2.0-2561'
    options.force_repo_version = None

    # Case when server is not running
    is_server_runing_mock.return_value = False, None
    try:
      set_current(options)
      self.fail("Server is not running - should error out")
    except FatalException:
      pass  # expected


    is_server_runing_mock.return_value = True, 11111

    # Test insufficient options case
    no_finalize_options_set_mock.return_value = True
    try:
      set_current(options)
      self.fail("Should error out")
    except FatalException:
      pass  # expected

    no_finalize_options_set_mock.return_value = False

    # Test normal flow
    get_validated_string_input_mock.return_value = 'dummy_string'

    p = get_ambari_properties_mock.return_value
    p.get_property.side_effect = ["8080", "false"]

    get_ambari_server_api_base_mock.return_value = 'http://127.0.0.1:8080/api/v1/'
    get_verbose_mock.retun_value = False

    set_current(options)

    self.assertTrue(urlopen_mock.called)
    request = urlopen_mock.call_args_list[0][0][0]
    self.assertEquals(request._Request__original, 'http://127.0.0.1:8080/api/v1/clusters/cc/stack_versions')
    self.assertEquals(request.data, '{"ClusterStackVersions": {"state": "CURRENT", "repository_version": "HDP-2.2.2.0-2561", "force": "false"}}')
    self.assertEquals(request.origin_req_host, '127.0.0.1')
    self.assertEquals(request.headers, {'X-requested-by': 'ambari', 'Authorization': 'Basic ZHVtbXlfc3RyaW5nOmR1bW15X3N0cmluZw=='})

  @patch("ambari_server.serverUpgrade.is_server_runing")
  @patch('ambari_server.serverUpgrade.SetCurrentVersionOptions.no_finalize_options_set')
  @patch('ambari_server.serverUpgrade.get_validated_string_input')
  @patch('ambari_server.serverUpgrade.get_ambari_properties')
  @patch('ambari_server.serverUtils.get_ambari_server_api_base')
  @patch('ambari_commons.logging_utils.get_verbose')
  @patch('urllib2.urlopen')
  def test_set_current_with_force(self, urlopen_mock, get_verbose_mock, get_ambari_server_api_base_mock,
                       get_ambari_properties_mock, get_validated_string_input_mock,
                       no_finalize_options_set_mock, is_server_runing_mock):
    options = MagicMock()
    options.cluster_name = 'cc'
    options.desired_repo_version = 'HDP-2.2.2.0-2561'
    options.force_repo_version = True

    # Case when server is not running
    is_server_runing_mock.return_value = False, None
    try:
      set_current(options)
      self.fail("Server is not running - should error out")
    except FatalException:
      pass  # expected


    is_server_runing_mock.return_value = True, 11111

    # Test insufficient options case
    no_finalize_options_set_mock.return_value = True
    try:
      set_current(options)
      self.fail("Should error out")
    except FatalException:
      pass  # expected

    no_finalize_options_set_mock.return_value = False

    # Test normal flow
    get_validated_string_input_mock.return_value = 'dummy_string'

    p = get_ambari_properties_mock.return_value
    p.get_property.side_effect = ["8080", "false"]

    get_ambari_server_api_base_mock.return_value = 'http://127.0.0.1:8080/api/v1/'
    get_verbose_mock.retun_value = False

    set_current(options)

    self.assertTrue(urlopen_mock.called)
    request = urlopen_mock.call_args_list[0][0][0]
    self.assertEquals(request._Request__original, 'http://127.0.0.1:8080/api/v1/clusters/cc/stack_versions')
    self.assertEquals(request.data, '{"ClusterStackVersions": {"state": "CURRENT", "repository_version": "HDP-2.2.2.0-2561", "force": "true"}}')
    self.assertEquals(request.origin_req_host, '127.0.0.1')
    self.assertEquals(request.headers, {'X-requested-by': 'ambari', 'Authorization': 'Basic ZHVtbXlfc3RyaW5nOmR1bW15X3N0cmluZw=='})

  @patch("ambari_server.serverUpgrade.run_os_command")
  @patch("ambari_server.serverUpgrade.get_java_exe_path")
  @patch("ambari_server.serverConfiguration.get_ambari_properties")
  @patch("ambari_server.serverUpgrade.get_ambari_properties")
  @patch("ambari_server.serverUpgrade.check_database_name_property")
  @patch("ambari_server.serverUpgrade.is_root")
  def test_upgrade_stack(self, is_root_mock, c_d_n_p_mock, up_g_a_p_mock, server_g_a_p_mock, java_path_mock, run_os_mock):

    run_os_mock.return_value = 0, "", ""

    java_path_mock.return_value = ""

    is_root_mock.return_value = True

    def do_nothing():
      pass
    c_d_n_p_mock.side_effect = do_nothing

    p = ambari_server.properties.Properties()
    p._props = {
      ambari_server.serverConfiguration.JDBC_DATABASE_PROPERTY: "mysql",
      ambari_server.serverConfiguration.JDBC_DATABASE_NAME_PROPERTY: "ambari"
    }

    up_g_a_p_mock.side_effect = [p, p]
    server_g_a_p_mock.side_effect = [p]

    args = ["upgrade_stack", "HDP-2.3"]
    upgrade_stack(args)

    self.assertTrue(run_os_mock.called)
    command = run_os_mock.call_args_list[0][0][0]
    self.assertTrue("StackUpgradeHelper" in command and "HDP" in command and "2.3" in command)

  def testCurrentVersionOptions(self):
    # Negative test cases
    options = MagicMock()
    options.cluster_name = None
    options.desired_repo_version = 'HDP-2.2.2.0-2561'
    cvo = SetCurrentVersionOptions(options)
    self.assertTrue(cvo.no_finalize_options_set())

    options = MagicMock()
    options.cluster_name = 'cc'
    options.desired_repo_version = None
    cvo = SetCurrentVersionOptions(options)
    self.assertTrue(cvo.no_finalize_options_set())

    # Positive test case
    options = MagicMock()
    options.cluster_name = 'cc'
    options.desired_repo_version = 'HDP-2.2.2.0-2561'
    cvo = SetCurrentVersionOptions(options)
    self.assertFalse(cvo.no_finalize_options_set())
