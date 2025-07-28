package com.tadikamesra.model;

public class LaporanAkademik {
    private int id;
    private int siswaId;
    private String namaSiswa;
    private String namaMapel;
    private String namaKelas;
    private int absen;
    private int tugas;
    private int quiz;
    private int uts;
    private int uas;
    private int nilai;
    private String grade;

    public LaporanAkademik(int id, int siswaId, String namaSiswa, String namaMapel, String namaKelas,
                           int absen, int tugas, int quiz, int uts, int uas, int nilai, String grade) {
        this.id = id;
        this.siswaId = siswaId;
        this.namaSiswa = namaSiswa;
        this.namaMapel = namaMapel;
        this.namaKelas = namaKelas;
        this.absen = absen;
        this.tugas = tugas;
        this.quiz = quiz;
        this.uts = uts;
        this.uas = uas;
        this.nilai = nilai;
        this.grade = grade;
    }

    // ✅ Getter Lengkap
    public int getId() { return id; }
    public int getSiswaId() { return siswaId; }
    public String getNamaSiswa() { return namaSiswa; }
    public String getNamaMapel() { return namaMapel; }
    public String getNamaKelas() { return namaKelas; }
    public int getAbsen() { return absen; }
    public int getTugas() { return tugas; }
    public int getQuiz() { return quiz; }
    public int getUts() { return uts; }
    public int getUas() { return uas; }
    public int getNilai() { return nilai; }
    public String getGrade() { return grade; }

    // Optional: Tambahkan setter jika Anda ingin support edit/update data
}
