package com.example.assignment.integration;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses( { PostIntegrationTest.class, CommentIntegrationTest.class, DocumentIntegrationTest.class } )
public class IntegrationTestSuite {
}
