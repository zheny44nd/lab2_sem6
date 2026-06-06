@Transactional(readOnly = true)
public List<LessonResponseDto> getLessons(LocalDate startDate, LocalDate endDate, Long groupId, Long lectorId, int page, int size) {
    return lessonRepository.findLessonsWithFilters(
            startDate, endDate, groupId, lectorId, PageRequest.of(page, size))
            .getContent().stream()
            .map(this::mapToDtoWithAttendance) // Изменено: mapToDto -> mapToDtoWithAttendance
            .toList();
}