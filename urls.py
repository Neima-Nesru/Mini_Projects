""" Defines URL patterns for learning_logs """

from django.urls import path
from . import views

urlpatterns = [
        path('', views.index, name='index'),
        path('topics/', views.topics, name='topics'),
        path('topics/<str:pk>', views.topic, name='topic'),
        path('new_topic/', views.new_topic, name='new_topic'),
        path('new_entry/<str:pk>', views.new_entry, name='new_entry'),
        path('edit_entry/<str:pk>', views.edit_entry, name='edit_entry')
        ]
