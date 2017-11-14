from django.db import models


class Note(models.Model):
    name = models.CharField(max_length=25, blank=True, default='Untitled Note')
    text = models.TextField(blank=True, default='')
    owner = models.ForeignKey('auth.User', related_name='notes', on_delete=models.CASCADE)



    class Meta:
        ordering = ('name',)
