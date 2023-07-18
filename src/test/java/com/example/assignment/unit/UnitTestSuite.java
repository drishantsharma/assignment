package com.example.assignment.unit;

import com.example.assignment.unit.controller.CommentControllerTest;
import com.example.assignment.unit.controller.DocumentControllerTest;
import com.example.assignment.unit.controller.PostControllerTest;
import com.example.assignment.unit.service.CommentServiceTest;
import com.example.assignment.unit.service.DocumentServiceTest;
import com.example.assignment.unit.service.PostServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses( { CommentControllerTest.class, DocumentControllerTest.class, PostControllerTest.class,
                    CommentServiceTest.class, DocumentServiceTest.class, PostServiceTest.class} )
public class UnitTestSuite {
}
