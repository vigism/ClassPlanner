from rest_framework import serializers
from note.models import Note
from django.contrib.auth.models import User


class NoteSerializer(serializers.ModelSerializer):

    owner = serializers.ReadOnlyField(source='owner.username')

    class Meta:
        model = Note
        fields = ('id', 'name', 'text', 'owner')


class UserSerializer(serializers.ModelSerializer):

    notes = serializers.StringRelatedField(many=True)
    password = serializers.CharField(write_only=True)

    def create(self, validated_data):
        user = User.objects.create(
            username=validated_data['username'],
        )
        user.set_password(validated_data['password'])
        user.save()

        return user

    class Meta:
        model = User
        fields = ('id', 'username', 'notes', 'password')
        write_only_fields = ('password',)
        read_only_fields = ('id',)

