from note.models import Note
from note.serializers import NoteSerializer, UserSerializer
from rest_framework import viewsets, permissions
from django.contrib.auth.models import User


class NoteViewSet(viewsets.ModelViewSet):

    serializer_class = NoteSerializer
    permission_classes = (permissions.IsAuthenticated,)

    def get_queryset(self):
        user = self.request.user
        return Note.objects.filter(owner=user)

    def perform_create(self, serializer):
        serializer.save(owner=self.request.user)


class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer





